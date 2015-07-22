package service

import dao.LecturesListDao
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

trait LecturesRepo {
  val lectureListDao: LecturesListDao
}

case class LecturesListService() extends LecturesRepo {
//  wiem że może to nadmiar posiadanie tych 3 warstw: controllera,
// service i dao ale może kiedyś sie przyda a teraz prościej będzie teraz uniknąć konfliktów
  def findAll: Future[List[BSONDocument]] = {
    lectureListDao.findAll
  }

  val lectureListDao = LecturesListDao()
}
