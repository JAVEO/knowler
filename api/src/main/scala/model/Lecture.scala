package model

case class Lecture(name: String, description: String) {
  require(!name.isEmpty, "lecture name must not be empty")
  require(description.length > 5, "description must be longer than 5")
}
