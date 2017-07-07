package com.example.stream

import java.nio.file.{Path, Paths}

import akka.stream.IOResult
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString

import scala.concurrent.Future

class ReadFileSource {
  private val filePath: Path = Paths.get("/com/example/yellow_tripdata_2016-12.csv")

  def source(): Source[ByteString, Future[IOResult]] = FileIO.fromPath(filePath)
}
