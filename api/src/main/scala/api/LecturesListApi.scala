package api

import akka.http.scaladsl.server.Directives._
import service.LecturesListService
import upickle.default._

trait LecturesListApi {
  val lecturesListRoute =
    (path("lectures") & get ) {
      onSuccess(LecturesListService.findAll){ result =>
        complete {
          write(result)
        }
      }
    }
}
