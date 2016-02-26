package dao

import model.LectureCreate
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson._
import _root_.utils.Database
import org.joda.time.DateTime
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object LecturesCreateDao {
  implicit object LectureWriter extends BSONDocumentWriter[LectureCreate] {
    def write(lecture: LectureCreate): BSONDocument = BSONDocument(
      "videoUrl" -> lecture.videoId,
      "title" -> lecture.title,
      "createdAt" -> DateTime.now.toString,
      "fileName" -> lecture.fileName)
  }

  def create(lecture: LectureCreate): Future[WriteResult] =
    Database.collection.insert(lecture)
}