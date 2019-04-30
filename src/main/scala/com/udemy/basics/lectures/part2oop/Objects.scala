package com.udemy.basics.lectures.part2oop

object Objects extends App {

  object Person {
    val EYES = 2

    def canFly = false

    // Factory method
    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }

  class Person(name: String = "Bobbie") {
    //instance level functionality
  }
  //COMPANIONS

  println(Person.EYES)
  println(Person canFly)

  println(Person == new Person)
  println(Person eq new Person)
  println(Person equals new Person)

  val elen = new Person("Elen")

  val fransua = new Person("Fransua")

  val child = Person(elen, fransua)

  // Scala application = scala object with
  // def main(array: Array[String]): Unit =


}
