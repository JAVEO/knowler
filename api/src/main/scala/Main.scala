
import akka.http.scaladsl.model.HttpHeader
import akka.http.scaladsl.model.headers.{`Access-Control-Allow-Credentials`, `Access-Control-Max-Age`, `Access-Control-Allow-Headers`}
import akka.stream.ActorMaterializer
import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import utils.{CorsSupport, Config}
import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Directives._

object Main extends App with Config with Routes {
  protected implicit val executor: ExecutionContext = system.dispatcher
  protected val log: LoggingAdapter = Logging(system, getClass)

  Http().bindAndHandle(handler = logRequestResult("log")(routes(fileUploadDirectory)), interface = httpInterface, port = httpPort)
}