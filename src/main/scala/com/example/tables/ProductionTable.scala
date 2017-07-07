package com.example.tables

import slick.jdbc.H2Profile.api._
import slick.lifted.{ProvenShape, Tag}

trait ProductionTable {

  class Production(tag: Tag) extends Table[(String, String, String)](tag, "PRODUCTION") {
    def producto: Rep[String] = column[String]("producto")

    def numero: Rep[String] = column[String]("numero")

    def feinicia: Rep[String] = column[String]("feinicia")

    def * : ProvenShape[(String, String, String)] = (producto, numero, feinicia)
  }

  val productions = TableQuery[Production]

}

