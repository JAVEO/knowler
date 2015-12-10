name := "knowler"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= {
  val akkaStreamVersion = "2.0-M2"

  Seq(
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-experimental"               % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamVersion,
    "org.scalatest"     %% "scalatest"                            % "2.2.5" % "test",
    "org.reactivemongo" %% "reactivemongo" % "0.11.3",
    "com.lihaoyi" %% "upickle" % "0.3.4",
    "joda-time" % "joda-time" % "2.4",
    "org.joda" % "joda-convert" % "1.6"
  )
}