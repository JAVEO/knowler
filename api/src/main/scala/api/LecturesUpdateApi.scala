package api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber

trait LecturesUpdateApi {
  val lectureUpdateRoute = (path("lectures"/ IntNumber ) & patch) { lectureId =>
    complete("Trying to update lecture with id: " + lectureId)
  }
}
