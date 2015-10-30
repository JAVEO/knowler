package service

import dao.LecturesCreateDao
import model.LectureCreate
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

object LecturesCreateService {
  def create(lecture: LectureCreate): Future[WriteResult] =
    LecturesCreateDao.create(lecture)
}
