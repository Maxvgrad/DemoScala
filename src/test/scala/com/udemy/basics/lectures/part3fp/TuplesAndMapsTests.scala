package com.udemy.basics.lectures.part3fp

import java.util.NoSuchElementException

import org.scalatest.FlatSpec

class TuplesAndMapsTests extends FlatSpec {

  // tuple is finite ordered list

  "Tuple" should " work like a charm" in {

    val tuple2 = (1, "value")
    val tuple3 = (1, "value", "value3")

    assertResult("value3")(tuple3._3)
    assertResult("Hello")(tuple2.copy(_2 = "Hello")._2)
  }

  "Map" should " work like a charm" in {
    val aPhoneBook: Map[String, String] = Map("Max" -> "89528850906", "Jim"->"952")

    assertResult(true)(aPhoneBook.contains("Max"))

    assertThrows[NoSuchElementException] {
      aPhoneBook("Vano")
    }
  }

  "Map" should "add pairing" in {
    //given
    val aPhoneBook: Map[String, String] = Map("Max" -> "89528850906", "Jim"->"952")
    val aPairing = "Mary" -> 679

    assertResult(679) {
      val updatedPhoneBook = aPhoneBook + aPairing
      updatedPhoneBook("Mary")
    }

    assertResult(false) {
      val updatedPhoneBook = aPhoneBook + aPairing
      updatedPhoneBook.values.forall((v: Any) => v.getClass == "".getClass)
    }
  }

  "Map" should "map as charm" in {
    val aPhoneBook: Map[String, Int] = Map("Max" -> 44, "Jim"->55)

    assertResult(true) {
      aPhoneBook.map(p => p.copy(p._1.toLowerCase(), p._2)).contains("max")
    }
  }

  "Map" should "filter keys" in {
    val aPhoneBook: Map[String, Int] = Map("Max" -> 44, "Jim"->55)

    assertResult(1) {
      aPhoneBook.filterKeys(k => k.startsWith("J")).size
    }
  }

  "Map" should "map values" in {
    val aMap: Map[String, String] = Map("Max" -> "7", "Jim"->"+9")

    assertResult(true) {
      aMap.mapValues(v => if (v.startsWith("+")) v else "+" + v).forall(p => p._2.startsWith("+"))
    }
  }

  "Map" should "group by" in {
    val cities = List(City("RU", "Moscow"), City("RU", "Tomsk"), City("RU", "Novosibirsk"), City("US", "NY"), City("US", "Boston"))

    assertResult(3) {
      cities.groupBy(c => c.country)("RU").size
    }
  }

  "Map" should "map key overlap" in {
    val phoneBook = Map("Jim" -> 1, "JIM" -> 2)

    assertResult(1) {
      phoneBook.map(p => p.copy(p._1.toLowerCase)).size
    }
  }

  class City(val country: String, val name: String) {}
  object City {
    def apply(country: String, name: String): City = new City(country, name)
  }

}
