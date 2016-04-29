package controllers

import com.google.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.libs.json.{JsObject, Json}
import play.api.libs.ws.WSClient
import play.api.mvc.{Controller, Result}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json.collection.JSONCollection
import play.modules.reactivemongo._
import play.api.libs.json._
import reactivemongo.play.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

@Singleton
class Users @Inject()(
  val reactiveMongoApi: ReactiveMongoApi,
  val ws: WSClient,
  val configuration: Configuration)
  extends Controller
  with MongoController
  with ReactiveMongoComponents
  with Security {

  val idTransformer = (__ \ '$oid).json.pick

  val valuesTransformer = __.read[JsArray].map {
    case JsArray(values) =>
      JsArray(values.map(e => e.transform(idTransformer).get))
  }

  val profileTransformer = (__ \ 'favorites).json.update(valuesTransformer)

  def profile = AuthAction.async { request =>
    users.find(Json.obj("email" -> request.userEmail))
      .cursor[JsObject]()
      .collect[List]()
      .map(profileResult)
  }

  def profileResult(list: List[JsObject]): Result =
    list.headOption
      .map(_.transform(profileTransformer).get)
      .map(u => Ok(u))
      .getOrElse(NotFound(""))

  def like(id: String) = AuthAction.async { request =>
    users.update(
      Json.obj("email" -> request.userEmail),
      Json.obj("$addToSet" -> Json.obj("favorites" -> BSONObjectID(id))))
      .map(r => Ok(""))
  }

  def dislike(id: String) = AuthAction.async { request =>
    users.update(
      Json.obj("email" -> request.userEmail),
      Json.obj("$pull" -> Json.obj("favorites" -> BSONObjectID(id))))
      .map(r => Ok(""))
  }

  def addUser() = AuthAction.async(parse.json) { request =>
    users.insert(request.body.as[JsObject])
      .map(result => Created("ok"))
  }

  def users: JSONCollection =
    db.collection[JSONCollection]("users")
}
