package controllers

import com.google.inject.{Inject, Singleton}
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
class Lectures @Inject() (val reactiveMongoApi: ReactiveMongoApi, ws: WSClient)
  extends Controller with MongoController with ReactiveMongoComponents {

  def list(phrase: Option[String]) = Action.async {
    def query = phrase.map(p =>
      Json.obj("$or" -> Json.arr(
        Json.obj("title" -> Json.obj("$regex" -> p)),
        Json.obj("description" -> Json.obj("$regex" -> p)))
      )).getOrElse(Json.obj())

    collection.find(query)
      .cursor[JsObject]()
      .collect[List]()
      .map(list => list.map(_.transform(idTransformer).get))
      .map(list => Ok(Json.toJson(list)))
  }

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
    findById(id)
      .map {
        case head :: Nil => head.transform(idTransformer).map(Ok(_)).get
        case Nil => NotFound
        case _ => BadRequest
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
    ws.url("https://drive.google.com/uc?id=" + fileId).getStream().map {
      case (response, body) =>
        Result(ResponseHeader(response.status, response.headers.mapValues(_.head)), body)
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
}
