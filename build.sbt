name := "Spark Testing"

scalaVersion := "2.11.6"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.3.0" % "provided"

libraryDependencies += "org.apache.spark" %% "spark-graphx" % "1.3.0" % "provided"

libraryDependencies += "org.apache.spark" %% "spark-mllib" % "1.3.0" % "provided"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.3.0" % "provided"

libraryDependencies += "org.apache.spark" %% "spark-hive" % "1.3.0" % "provided"

libraryDependencies += "org.spire-math" %% "cats-core" % "0.1.0-SNAPSHOT"