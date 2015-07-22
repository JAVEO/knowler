package dao

import utils.Database

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.bson.BSONDocument
import reactivemongo.api.collections.bson.BSONCollection

case class LecturesListDao(){
  def findAll: Future[List[BSONDocument]] = {
    val query = BSONDocument()
    val filter = BSONDocument()

    Database.collection
      .find(query, filter)
      .cursor[BSONDocument]
      .collect[List]()
  }
}
