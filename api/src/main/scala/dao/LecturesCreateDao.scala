package dao

import model.Lecture
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocumentWriter, BSONDocument}
import utils.Database

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object LecturesCreateDao {
  implicit object LectureWriter extends BSONDocumentWriter[Lecture] {
    def write(lecture: Lecture): BSONDocument = BSONDocument(
      "name" -> lecture.name, "description" -> lecture.description)
  }

  def create(lecture: Lecture): Future[WriteResult] =
    Database.collection.insert(lecture)
}