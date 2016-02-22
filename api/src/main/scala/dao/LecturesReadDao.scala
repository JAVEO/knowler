package dao

import model.LectureRead
import reactivemongo.bson.{BSONObjectID, BSONDocument, BSONDocumentReader}
import utils.Database
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

object LecturesReadDao {
  implicit object LectureReader extends BSONDocumentReader[LectureRead] {
    def read(doc: BSONDocument): LectureRead = {
      val id = doc.getAs[BSONObjectID]("_id").get.stringify
      val videoUrl = doc.getAs[String]("videoUrl").get
      val title = doc.getAs[String]("title").get
      val createdAt = doc.getAs[String]("createdAt").get
      val fileName = doc.getAs[String]("fileName").get
      LectureRead(id, videoUrl, title, createdAt, fileName)
    }
  }

  def findById(id: String): Future[Option[LectureRead]] = {
    val query = BSONDocument("_id" -> BSONObjectID(id))
    findByQuery(query).map(_.headOption)
  }

  def findAll: Future[List[LectureRead]] = {
    val query = BSONDocument()
    findByQuery(query)
  }

  private def findByQuery(query: BSONDocument): Future[List[LectureRead]] = {
    Database.collection.find(query).cursor[LectureRead].collect[List]()
  }
}