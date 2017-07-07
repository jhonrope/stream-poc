package com.example.repository

trait ProductionRepository extends Repository[String, String] {

  override def query(id: String) = ???

}
