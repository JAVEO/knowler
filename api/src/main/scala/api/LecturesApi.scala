package api

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpRequest}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import model.LectureCreate
import reactivemongo.api.commands.WriteResult
import service.LecturesService
import upickle.default._
import akka.stream.{ActorMaterializer, Materializer}
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration._
import akka.http.scaladsl.unmarshalling.Unmarshaller
import scala.concurrent.ExecutionContext.Implicits.global

trait LecturesApi {
  implicit val upickleFromRequestUnmarshaller: FromRequestUnmarshaller[LectureCreate] = {
    implicit val system = ActorSystem()
    implicit val materializer: Materializer = ActorMaterializer()
    new Unmarshaller[HttpRequest, LectureCreate] {
      def apply(req: HttpRequest)(implicit ec: ExecutionContext): Future[LectureCreate] = {
        req.entity.withContentType(ContentTypes.`application/json`).toStrict(1.second)
          .map(_.data.toArray).map(x => read[LectureCreate](new String(x)))
      }
    }
  }

  val lectureCreateRoute = (path("lectures") & post & entity(as [LectureCreate])) { lecture =>
    complete(LecturesService.create(lecture).map((writeResult: WriteResult) => "ok"))
  }

  val lecturesListRoute =
    (path("lectures") & get ) {
      onSuccess(LecturesService.findAll){ result =>
        complete {
          write(result)
        }
      }
    }

  val lectureUpdateRoute = (path("lectures"/ IntNumber ) & patch) { lectureId =>
    complete("Trying to update lecture with id: " + lectureId)
  }

  val lectureDetailsRoute =
    path("lectures"/Rest) { lectureId =>
      get {
        onSuccess(LecturesService.findById(lectureId)) {
          case Some(result) =>
            complete(write(result))
          case _ =>
            complete("Not found")
        }
      }
    }

  val lectureDeleteRoute = (path("lectures"/ IntNumber ) & delete) { lectureId =>
    complete("Trying to delete lecture with id: " + lectureId)
  }
}
