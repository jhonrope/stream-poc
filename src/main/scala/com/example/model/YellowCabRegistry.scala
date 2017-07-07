package com.example.model

import com.example.model.YellowCabRegistry.{Miles, Money}

import scala.util.Try


case class YellowCabRegistry(VendorID: Int,
                             tpep_pickup_datetime: String,
                             tpep_dropoff_datetime: String,
                             passenger_count: Int,
                             trip_distance: Miles,
                             RatecodeID: Int,
                             store_and_fwd_flag: String,
                             PULocationID: String,
                             DOLocationID: String,
                             payment_type: Int,
                             fare_amount: Money,
                             extra: Money,
                             mta_tax: Money,
                             tip_amount: Money,
                             tolls_amount: Money,
                             improvement_surcharge: Money,
                             total_amount: Money) {
}

object YellowCabRegistry {
  type Miles = Double
  type Money = BigDecimal

  def fromCSV(csv: String): Try[YellowCabRegistry] = Try {
    val values = csv.split(",")

    YellowCabRegistry(
      values(0).toInt,
      values(1),
      values(2),
      values(3).toInt,
      values(4).toDouble,
      values(5).toInt,
      values(6),
      values(7),
      values(8),
      values(9).toInt,
      BigDecimal(values(10)),
      BigDecimal(values(11)),
      BigDecimal(values(12)),
      BigDecimal(values(13)),
      BigDecimal(values(14)),
      BigDecimal(values(15)),
      BigDecimal(values(15))
    )
  }

}