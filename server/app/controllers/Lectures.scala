package controllers

import com.google.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.modules.reactivemongo._
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Future

@Singleton
class Lectures @Inject() (
  val reactiveMongoApi: ReactiveMongoApi,
  val ws: WSClient,
  val configuration: Configuration)
  extends Controller
    with MongoController
    with ReactiveMongoComponents
    with Security {

  def list(search: Option[String], category: Option[String], author: Option[String], limit: Int, sort: Option[String]) = Action.async {
    val categoryQuery = category.map(c =>
      Json.obj("category" -> c))

    val authorQuery = author.map(a =>
      Json.obj("author.id" -> a))

    val searchQuery = search.map(p =>
      Json.obj("$text" -> Json.obj(
        "$search" -> p)
      ))

    val query = searchQuery
      .orElse(categoryQuery)
      .orElse(authorQuery)
      .getOrElse(Json.obj())

    val sortBy = sort.map {
      case "latest" => Json.obj("createdDate" -> -1)
      case "oldest" => Json.obj("createdDate" -> 1)
      case "mostPopular" => Json.obj("viewCount" -> -1)
    }.getOrElse(Json.obj("createdDate" -> -1))

    collection.find(query)
      .sort(sortBy)
      .cursor[JsObject]()
      .collect[List](limit)
      .map(list => list.map(_.transform(idTransformer).get))
      .map(list => Ok(Json.toJson(list)))
  }

  def favorites(user: String) = Action.async {
    favoritesIds(user)
      .flatMap(favoritesLectures)
      .map(list => Ok(Json.toJson(list)))
  }

  def favoritesLectures(ids: JsArray): Future[List[JsObject]] = {
    collection.find(Json.obj("_id" -> Json.obj("$in" -> ids)))
      .cursor[JsObject]()
      .collect[List]()
      .map(list => list.map(_.transform(idTransformer).get))
  }

  def favoritesIds(user: String): Future[JsArray] = {
    users.find(Json.obj("_id" -> user))
      .cursor[JsObject]()
      .collect[List]()
      .map(userFavorites)
  }

  def userFavorites(list: List[JsObject]): JsArray =
    list.headOption
      .flatMap(_.value.get("favorites"))
      .map(_.as[JsArray])
      .getOrElse(Json.arr())

  def create = Action.async(parse.json) { implicit request =>
    val id = BSONObjectID.generate
    val _id = BSONObjectIDFormat.partialWrites(id)
    val document = request.body.as[JsObject]
    val docWithId = document.+("_id" -> _id)
    collection.insert(docWithId)
      .map(result => Created(_id)
        .withHeaders(LOCATION -> routes.Lectures.details(id.stringify).absoluteURL()))
  }

  def details(id: String) = Action.async {
    collection.findAndUpdate(idSelector(id), Json.obj("$inc" -> Json.obj("viewCount" -> 1)), fetchNewObject = true)
      .map { result =>
        result.value.map(_.transform(idTransformer).get).map(Ok(_)).getOrElse(NotFound)
      }
  }

  def delete(id: String) = Action.async {
    val idSel = idSelector(id)
    collection.remove(idSel).map(r => Ok(idSel))
  }

  def update(id: String) = Action.async(parse.json) { request =>
    findById(id)
      .flatMap {
        case head :: Nil => collection.update(idSelector(id), request.body.as[JsObject]).map(r => Ok(toJson(r)))
        case Nil => Future.successful(NotFound)
        case _ => Future.successful(BadRequest)
      }
  }

  def download(fileId: String) = Action.async {
    ws.url(googleDriveUrl + fileId).withFollowRedirects(true).getStream().map {
      case (response, body) =>
        if (response.status == OK) {
          response.headers.get(CONTENT_LENGTH) match {
            case Some(Seq(length)) => Ok.feed(body).as("application/pdf").withHeaders(CONTENT_LENGTH -> length)
            case _ => Ok.chunked(body)
          }
        } else BadGateway
    }
  }

  def findById(id: String): Future[List[JsObject]] = {
    collection.find(idSelector(id))
      .cursor[JsObject]()
      .collect[List]()
  }

  val idTransformer = __.json.update(
    (__ \ 'id).json.copyFrom( (__ \ '_id \ '$oid).json.pick ))
    .andThen((__ \ '_id).json.prune)

  def idSelector(id: String): JsObject =
    Json.obj("_id" -> Json.obj("$oid" -> id))

  def toJson(result: WriteResult): JsObject =
    Json.obj("status" -> result.ok)

  def collection: JSONCollection =
    db.collection[JSONCollection]("lectures")

  def users: JSONCollection =
    db.collection[JSONCollection]("users")
}
