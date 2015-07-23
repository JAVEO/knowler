package utils

import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api._
import scala.concurrent.ExecutionContext.Implicits.global

object Database {
  val collection = connect()

  def connect(): BSONCollection = {
    val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))
    val db = connection("knowler")

    db.collection("lectures")
  }
}
