package api

import akka.http.scaladsl.server.Directives._

trait LecturesApi {
  val lecturesRoutes =
    (path("lectures") & get) {
      complete("someResponse")
    }
}
