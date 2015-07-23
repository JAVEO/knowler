import akka.http.scaladsl.server.Directives._
import api._

trait Routes extends LecturesListApi
            with LecturesDetailsApi
            with LecturesCreateApi
            with LecturesDeleteApi
            with LecturesUpdateApi {

  val routes = pathPrefix("v1") {
    lecturesListRoute ~
    lectureDetailsRoute ~
    lectureCreateRoute ~
    lectureDeleteRoute ~
    lectureUpdateRoute
  } ~ path("")(getFromResource("public/index.html"))
}
