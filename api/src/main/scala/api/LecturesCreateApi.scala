package api

import akka.http.scaladsl.server.Directives._

trait LecturesCreateApi {
  val lectureCreateRoute = (path("lectures") & post) {
    complete("Trying to create lecture")
  }
}
