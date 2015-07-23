package api
//import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import model.Lecture
import spray.json.DefaultJsonProtocol


trait LectureJsonFormat extends DefaultJsonProtocol{
  implicit val lectureFormat = jsonFormat2(Lecture.apply)
}
