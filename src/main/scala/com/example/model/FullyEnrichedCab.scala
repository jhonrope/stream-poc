package com.example.model

import com.example.model.YellowCabRegistry.Money

/**
  * Created by Jhony on 03/06/2017.
  */
case class FullyEnrichedCab(cabDescription: CabDescription, pricePerDistance: Option[Double])

object FullyEnrichedCab {
  def pricePerDistanceRatio(totalAmount: Money, tripDistance: Double): Option[Double] = {
    if (tripDistance <= 0d) {
      None
    } else {
      Some((totalAmount / tripDistance).toDouble)
    }
  }
}