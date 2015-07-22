name := "knowler"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= {
  val akkaStreamVersion = "1.0"
  val akkaVersion = "2.3.12"

  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-experimental"               % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"       % akkaStreamVersion,
    "org.scalatest"     %% "scalatest"                            % "2.2.5" % "test",
    "com.typesafe.akka" %% "akka-testkit"                         % akkaVersion % "test",
    "org.reactivemongo" %% "reactivemongo" % "0.11.3"
  )
}