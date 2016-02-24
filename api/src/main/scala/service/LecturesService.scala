package service

import dao.{LecturesReadDao, LecturesCreateDao}
import model.{LectureRead, LectureCreate}
import reactivemongo.api.commands.WriteResult
import scala.concurrent.Future

object LecturesService {
  def create(lecture: LectureCreate): Future[WriteResult] =
    LecturesCreateDao.create(lecture)

  def findAll: Future[List[LectureRead]] =
    LecturesReadDao.findAll

  def findById(id: String): Future[Option[LectureRead]] =
    LecturesReadDao.findById(id)
}
