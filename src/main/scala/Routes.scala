import akka.http.scaladsl.server.Directives._
import api.LecturesApi

trait Routes extends LecturesApi {
  val routes = pathPrefix("v1") {
    lecturesRoutes
  } ~ path("")(getFromResource("public/index.html"))
}
