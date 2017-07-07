package com.example.stream

import akka.NotUsed
import akka.stream.scaladsl._
import slick.basic.DatabasePublisher


class ProductionSource(db: DatabasePublisher[(String, String, String)]) {

  val productionSource: Source[(String, String, String), NotUsed] = Source.fromPublisher {
    db
  }
}
