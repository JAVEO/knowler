
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import utils.Config
import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Directives._

object Main extends App with Config with Routes {
  protected implicit val executor: ExecutionContext = system.dispatcher
  protected val log: LoggingAdapter = Logging(system, getClass)

  Http().bindAndHandle(handler = logRequestResult("log")(routes(fileUploadDirectory)), interface = httpInterface, port = httpPort)
}