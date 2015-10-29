package service

import dao.LecturesListDao
import model.LectureRead
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

object LecturesListService {
  def findAll: Future[List[LectureRead]] = {
    LecturesListDao.findAll
  }
}
