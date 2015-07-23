package service

import dao.LecturesListDao
import model.Lecture
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

object LecturesListService {
  def findAll: Future[List[Lecture]] = {
    LecturesListDao.findAll
  }
}
