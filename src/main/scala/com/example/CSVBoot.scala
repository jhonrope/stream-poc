package com.example

import java.io.File
import java.nio.file.{Files, Paths}

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, IOResult, Supervision}
import akka.stream.scaladsl.{FileIO, Flow, Framing, RunnableGraph, Sink, Source}
import akka.util.ByteString
import com.example.model.{FullyEnrichedCab, YellowCabRegistry}
import com.example.services.SomeFakeCall

import scala.concurrent.Future
import scala.util.Success

object CSVBoot extends App {

  val decider: Supervision.Decider = {
    case _: NumberFormatException => Supervision.Resume
    case _ => Supervision.Stop
  }

  implicit lazy val system = ActorSystem("reactive-streams-end-to-end")
  implicit lazy val materializer = ActorMaterializer(ActorMaterializerSettings(system).withSupervisionStrategy(decider))


  val path = Paths.get("yellow_tripdata_2016-12.csv")

  val graph: RunnableGraph[Future[IOResult]] = FileIO.fromPath(path)
    .via(Framing.delimiter(ByteString("\n"), 256, true).map(_.utf8String)).drop(1)
    .map(YellowCabRegistry.fromCSV).async
    .mapAsync(5) { case Success(ycr) => new SomeFakeCall().fakeApiCall(ycr) }
    .map {
      cd =>
        val pricePerDistance = FullyEnrichedCab.pricePerDistanceRatio(cd.yellowCabRegistry.total_amount, cd.yellowCabRegistry.trip_distance)
        FullyEnrichedCab(cd, pricePerDistance)
    }.async
    .to(Sink.fold(0) { (sum, element) =>
      val newSum = sum + 1
      if (newSum % 5000 == 0) {
        println(s"Count: $newSum")
      }
      newSum
    })

  graph.run()
}
