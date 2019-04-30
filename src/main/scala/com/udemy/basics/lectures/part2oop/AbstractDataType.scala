package com.udemy.basics.lectures.part2oop

object AbstractDataType extends App {

  // abstract

  abstract class Animal {
    val creatureType: String
    def eat: Unit
  }

  //val animal = new Animal

  class Dog extends Animal {
    override val creatureType: String = "Canine"
    override def eat: Unit = println("crunch crunch")
  }

  // traits
  // 1 - traits do not have a constructor parameter
  // 2 - multiple traits may be inherited by the same class
  // 3 - traits = behavior, abstract class = "thing"

  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  trait ColdBlooded

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"

    override def eat: Unit = println("omnomnom")

    override def eat(animal: Animal): Unit = println(s"I'm croc and i'm eating a ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile

  croc.eat(dog)

}
