package service

import dao.LecturesCreateDao
import model.Lecture
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

object LecturesCreateService {
  def create(lecture: Lecture): Future[WriteResult] =
    LecturesCreateDao.create(lecture)
}
