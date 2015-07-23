package api

import akka.http.scaladsl.server.Directives._

trait LecturesDetailsApi {
  val lectureDetailsRoute =
    (path("lectures"/ IntNumber ) & get) { lectureId =>
      complete("LectureId: " + lectureId)
    }
}
