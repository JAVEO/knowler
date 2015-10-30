package dao

import model.LectureRead
import utils.Database
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.bson.{BSONDocumentReader, BSONDocument}

object LecturesListDao {
  implicit object LectureReader extends BSONDocumentReader[LectureRead] {
    def read(doc: BSONDocument): LectureRead = {
      val videoUrl = doc.getAs[String]("videoUrl").get
      val title = doc.getAs[String]("title").get
      val createdAt = doc.getAs[String]("createdAt").get
      val fileName = doc.getAs[String]("fileName").get
      LectureRead(videoUrl, title, createdAt, fileName)
    }
  }

  def findAll: Future[List[LectureRead]] = {
    val query = BSONDocument()
    Database.collection.find(query).cursor[LectureRead].collect[List]()
  }
}
