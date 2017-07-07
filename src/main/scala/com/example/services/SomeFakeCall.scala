package com.example.services

import com.example.model.{CabDescription, YellowCabRegistry}

import scala.concurrent.Future
import scala.util.Random


class SomeFakeCall {

  def fakeApiCall(register: (String, String, String)): Future[(String, String, String, String)] = {
    Future.successful(register._1, register._2, register._3, Random.nextString(20))
  }

  def fakeApiCall(yellowCabRegistry: YellowCabRegistry): Future[CabDescription] = {
    Future.successful(
      CabDescription(yellowCabRegistry,
        "I’m a fake description of some sort from calling a fake Future API.")
    )
  }
}
