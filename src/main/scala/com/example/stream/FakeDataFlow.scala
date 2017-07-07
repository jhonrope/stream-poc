package com.example.stream

import akka.NotUsed
import akka.stream.scaladsl.Flow
import com.example.Boot.{NewProd, Prod}

import scala.concurrent.Future

class FakeDataFlow {

  def addNewDataFlow(f: Prod => Future[NewProd]): Flow[Prod, NewProd, NotUsed] = {
    Flow[Prod].mapAsync(parallelism = 5)(f)
  }

}
