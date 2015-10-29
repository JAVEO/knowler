package model

case class LectureRead(videoUrl: String, title: String, createdAt: String, fileName: String) {
  require(!videoUrl.isEmpty, "lecture videoUrl must not be empty")
  require(!title.isEmpty, "lecture title must not be empty")
  require(!fileName.isEmpty, "lecture filename must not be empty")
}

case class LectureCreate(videoUrl: String, title: String, fileName: String) {
  require(!videoUrl.isEmpty, "lecture videoUrl must not be empty")
  require(!title.isEmpty, "lecture title must not be empty")
  require(!fileName.isEmpty, "lecture filename must not be empty")
}
