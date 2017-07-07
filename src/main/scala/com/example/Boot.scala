package com.example

import java.nio.file.Paths

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl.{FileIO, Framing, RunnableGraph, Sink, Source}
import akka.util.ByteString
import com.example.services.SomeFakeCall
import com.example.stream.{FakeDataFlow, ProductionSource, ReadFileSource}
import com.example.tables.ProductionTable
import slick.basic.DatabasePublisher

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import slick.jdbc.H2Profile.api._

/**
  * Created by Jhony on 02/06/2017.
  */
object Boot extends App with ProductionTable {

  implicit lazy val system = ActorSystem("reactive-streams-end-to-end")
  implicit lazy val materializer = ActorMaterializer()

  type Prod = (String, String, String)
  type NewProd = (String, String, String, String)
  println("Hola Mundo")

  val db = Database.forConfig("h2mem1")

  val setup = DataBaseSetup.setup()

  val future = db.run(setup)

  Await.ready(future, Duration.Inf)

  //  val publisher = db.stream(productions.result.withStatementParameters(fetchSize = 10)).foreach(println)

  val q = for (r <- productions) yield r.*
  val a = q.result.withStatementParameters(fetchSize = 10)
  val p: DatabasePublisher[(String, String, String)] = db.stream(a)

  val source = new ProductionSource(p).productionSource
  val flow = new FakeDataFlow().addNewDataFlow(new SomeFakeCall().fakeApiCall)
  val sink = Sink.foreach(println)
  /*  db.run(productions.result).map(_.foreach {
      case (name, numero, fecha) =>
        println(name, numero, fecha)
    })*/

  val solution: RunnableGraph[NotUsed] = source.via(flow).to(sink)

  FileIO.fromPath(Paths.get("/com/example/yellow_tripdata_2016-12.csv"))
    .via(Framing.delimiter(ByteString("\n"), 256, true))
    .to(Sink.foreach(println))
    .run()

  //  solution.run()

  //  val otherSource: Source[ByteString, Future[IOResult]] = new ReadFileSource().source()
  //
  //  val otherGraph = otherSource.to(sink)
  //
  //  otherGraph.run()
  //
  //  Thread.sleep(1000)

}
