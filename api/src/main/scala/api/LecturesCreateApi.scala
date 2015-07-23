package api

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpRequest}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import model.Lecture
import upickle.default._
import akka.stream.{ActorMaterializer, Materializer}
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration._
import akka.http.scaladsl.unmarshalling.Unmarshaller

trait LecturesCreateApi {
  implicit val upickleFromRequestUnmarshaller: FromRequestUnmarshaller[Lecture] = {
    implicit val system = ActorSystem()
    implicit val materializer: Materializer = ActorMaterializer()
    new Unmarshaller[HttpRequest, Lecture] {
      def apply(req: HttpRequest)(implicit ec: ExecutionContext): Future[Lecture] = {
        req.entity.withContentType(ContentTypes.`application/json`).toStrict(1.second)
          .map(_.data.toArray).map(x => read[Lecture](new String(x)))
      }
    }
  }

  val lectureCreateRoute = (path("lectures") & post & entity(as [Lecture])) { lecture =>
    complete(write(lecture))
  }
}
