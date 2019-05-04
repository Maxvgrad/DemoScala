package com.udemy.basics.lectures.part4pmatching

import org.scalatest.FlatSpec

import scala.util.Random

class PatternMatchingTests extends FlatSpec {

  it should "work with pattern matching" in {

    val description = new Random().nextInt(5) match {
      case 0 => "one"
      case 1 => "two"
      case 3 => "three"
      case 4 => "four"
      case 5 => "five"
      case _ => "ARRRR"
    }

    println(description)
  }

  it should "decompose values" in  {

    case class Person(name: String, age: Int)

    val bob = Person("bob", 50)

    val greeting = bob match {
      case Person(name, age) => s"Hello i'm $name and i'm $age years old"
      case _ => "Unknown class"
    }

    println(greeting)
  }

  it should "decompose values with a guard" in  {

    case class Person(name: String, age: Int)

    val bob = Person("bob", 20)

    val greeting = bob match {
      case Person(name, age) if age < 21 => s"Hello i'm $name and can not drink in the US"
      case Person(name, age) => s"Hello i'm $name and i'm $age years old"
      case _ => "Unknown class" //WILDCARD
    }
    assertResult("Hello i'm bob and can not drink in the US")(greeting)
  }

  it should "extractor pattern for case classes" in {

    sealed class Animal
    case class Dog(breed: String) extends Animal
    case class Parrot(greeting: String) extends Animal

    val dog: Animal = Parrot("Hello")

    dog match {
      case Dog(n) => println("Match dog")
      case Parrot(n) => println("Match parrot")
    }
  }

  it should "work with expressions" in {

  }

}
