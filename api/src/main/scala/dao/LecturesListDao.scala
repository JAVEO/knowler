package dao

import model.Lecture
import utils.Database

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.bson.{BSONDocumentReader, BSONDocument}

object LecturesListDao {
  implicit object LectureReader extends BSONDocumentReader[Lecture] {
    def read(doc: BSONDocument): Lecture = {
      val name = doc.getAs[String]("name").get
      val description = doc.getAs[String]("description").get
      Lecture(name, description)
    }
  }

  def findAll: Future[List[Lecture]] = {
    val query = BSONDocument()
    Database.collection.find(query).cursor[Lecture].collect[List]()
  }
}
