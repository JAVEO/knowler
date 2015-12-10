package api

import java.io.File

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.{Marshaller, ToResponseMarshaller}
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import akka.stream.scaladsl.{Sink, Source}
import akka.util.ByteString
import model.LectureCreate
import reactivemongo.api.commands.WriteResult
import service.LecturesService
import upickle.default._
import akka.stream.{ActorMaterializer, Materializer}
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import akka.http.scaladsl.unmarshalling.Unmarshaller
import scala.concurrent.ExecutionContext.Implicits.global

trait LecturesApi {
  implicit val system = ActorSystem()
  implicit val materializer: Materializer = ActorMaterializer()

  implicit val upickleFromRequestUnmarshaller: FromRequestUnmarshaller[LectureCreate] = {
    new Unmarshaller[HttpRequest, LectureCreate] {
      def apply(req: HttpRequest)(implicit ec: ExecutionContext, materializer: Materializer): Future[LectureCreate] = {
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
    path("lectures" / Rest) { lectureId =>
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

  implicit def stringStreamMarshaller(implicit ec: ExecutionContext): ToResponseMarshaller[Source[String, Any]] =
    Marshaller.withFixedCharset(MediaTypes.`text/plain`, HttpCharsets.`UTF-8`) { s =>
      HttpResponse(entity = HttpEntity.CloseDelimited(MediaTypes.`text/plain`, s.map(ByteString(_))))
    }

  def pdfUploadRoute(fileUploadDirectory: String) =
    path("lectures" / "pdf") { //TODO lectures/:lectureId/pdf
      post {
        entity(as[Multipart.FormData]) { entity =>
          val files: Source[String, Any] = entity.parts.mapAsync(parallelism = 4) { bodyPart =>
            val filePath = fileUploadDirectory + "/" + bodyPart.filename.getOrElse("temp")
            bodyPart.entity.dataBytes.runWith(Sink.file(new File(filePath))).map(a => filePath)
          }
          complete(files)
        }
      }
    }
}
