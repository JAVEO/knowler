package api

import akka.http.scaladsl.server.Directives._

trait LecturesDeleteApi {
  val lectureDeleteRoute = (path("lectures"/ IntNumber ) & delete) { lectureId =>
    complete("Trying to delete lecture with id: " + lectureId)
  }
}
