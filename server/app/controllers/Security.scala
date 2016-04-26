package controllers

import play.api.libs.ws.{WSClient, WSResponse}
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsObject

import scala.concurrent.Future

trait Security extends Conf {
  this: Controller =>

  def ws: WSClient

  class UserRequest[A](val userEmail: String, request: Request[A]) extends WrappedRequest[A](request)

  object UserRequestRefiner extends ActionRefiner[Request, UserRequest] {
    override protected def refine[A](request: Request[A]): Future[Either[Result, UserRequest[A]]] = {
      request.headers.get("oauth2-token")
        .map(checkToken(request, _))
        .getOrElse(Future.successful(Left(Unauthorized(""))))
    }

    def checkToken[A](request: Request[A], token: String): Future[Either[Result, UserRequest[A]]] = {
      ws.url(googleTokenUrl + token).execute()
        .flatMap(checkClientId(request, _))
    }

    def checkClientId[A](request: Request[A], response: WSResponse): Future[Either[Result, UserRequest[A]]] = {
      val responseValue = response.json.asInstanceOf[JsObject].value
      val userRequest = for {
        clientId <- responseValue.get("aud")
        if clientId.as[String] == googleClientId
        userEmail <- responseValue.get("email")
      } yield new UserRequest[A](userEmail.as[String], request)

      Future.successful(userRequest.toRight(Unauthorized("")))
    }
  }

  val AuthAction = Action andThen UserRequestRefiner
}
