package com.udemy.basics.lectures.part2oop

object CaseClasses extends App {

  case class Person(name: String, age: Int)

  // 1. class parameters are fields
  val jim = new Person("jim", 34)
  //

  println(jim.name)

  // 2. sensible toString
  println(jim.toString)
  println(jim) //syntactic sugar

  // 3. equals an hashCode implements OOTB (out of the box)

  val jim2 = new Person("jim", 34)

  println(jim.equals(jim2))
  println(jim == jim2)

  // 4. CCs (case classes) have handy copy method

  val jim3 = jim.copy(age = 55)
  println(jim3)

  // 5. CCs have companion objects
  val thePerson = Person
  val marry = Person("Mary", 43)

  println(thePerson)
  println(marry)

  // 6. CCs are serializable
  // Akka

  // 7. CCs have extractor patterns = CCs can be used in PATTERN MATCHING

  case object UnitedKingdom {
    def name: String  = "UK"
  }





}
