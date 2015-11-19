package service

import dao.{LecturesDetailsDao, LecturesListDao, LecturesCreateDao}
import model.{LectureRead, LectureCreate}
import reactivemongo.api.commands.WriteResult
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object LecturesService {
  def create(lecture: LectureCreate): Future[WriteResult] =
    LecturesCreateDao.create(lecture)

  def findAll: Future[List[LectureRead]] =
    LecturesListDao.findAll

  def findById(id: String): Future[Option[LectureRead]] =
    LecturesDetailsDao.findById(id).map(_.headOption)
}
