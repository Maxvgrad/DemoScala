package com.udemy.basics.lectures.part2oop

object Inheritance extends App {

  class Animal {
    val creatureType = "Wild"
    val predator = true

    final val finalConst = "Animal final constant"

    def eat = println("super Animal method nomnomnom")

    private def privateMethod = eat

    protected def protectedMethod = eat
  }

  class Cat extends Animal {

    def testAccessModifier() = {

      super.eat

      eat

      protectedMethod

      //println(privateMethod)
    }

    override def eat = println("override method from eat from Cat")
  }

  // Overriding

  class Dog(override val predator: Boolean = false) extends Animal {
    override val creatureType = "domestic"

    //override val finalConst = ""

    override def eat() = println("crunch, crunch")
  }

  val cat = new Cat

  cat testAccessModifier

  val dog = new Dog
  val unknownDog: Animal = new Dog

  println(dog eat)
  println(dog creatureType)

  println(dog finalConst)


  // Type substitution (broad: polymorphism)
  val dogAnimal: Animal = new Dog

  println(dogAnimal creatureType)
  println(dogAnimal predator)

  // Constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }

  class OfficeManager(name: String, age: Int, idCardNumber: Int) extends Person(name, age)

  class Child(name: String) extends Person(name)



  // Preventing overriding:
  // 1. final on member
  // 2. final on the entire class
  // 3. seal - allow extend class in this file but prevent from (like package private)

  sealed class Shape {
    val name = "Abstract shape"
  }

  class Rectangular(override val name: String = "Rectangular") extends Shape {

  }

  val shape: Shape = new Rectangular()

  println(shape name)

}
