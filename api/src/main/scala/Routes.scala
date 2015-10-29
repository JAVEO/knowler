import akka.http.scaladsl.server.Directives._
import api._

trait Routes extends LecturesApi {

  val routes = pathPrefix("v1") {
    lecturesListRoute ~
    lectureCreateRoute ~
    lectureDetailsRoute
  } ~ path("")(getFromResource("public/index.html"))
}
