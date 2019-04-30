package com.udemy.basics.lectures.part2oop

object AnonymousClasses extends App {

  trait Animal {
    def eat: Unit
  }

  // anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("om nom nom")
  }

  funnyAnimal.eat

  println(funnyAnimal.getClass)

  class Person(name: String) {
    def sayHi: Unit = println(s"Hello my name is $name")
  }

  val jim: Person = new Person("Jimmy") {
    override def sayHi: Unit = println("Bad ass!")
  }

  jim sayHi

}
