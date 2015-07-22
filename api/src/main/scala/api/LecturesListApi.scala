package api

import akka.http.scaladsl.marshalling.{Marshaller, ToResponseMarshallable}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import model.Lecture
import service.LecturesListService
import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
trait LecturesListApi extends LectureJsonFormat{

  val lecturesListRoute =
    (path("lectures") & get ) {
      onSuccess(LecturesListService().findAll){ result =>
        complete{
          implicit val lectureFormat = jsonFormat2(Lecture.apply)
          Lecture("fakename", "fakeDescription")
        }
      }
    }
}
