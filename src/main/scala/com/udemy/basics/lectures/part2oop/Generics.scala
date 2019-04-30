package com.udemy.basics.lectures.part2oop

object Generics {

  class MyList[+A] {
    //user type A inside class definition
    def add[B >: A](element: B): MyList[B] = ???
  }

  class MyMap[Key, Value]

  trait GenericTrait[A]

  val listOfIntegers = new MyList[Int]
  val listOfString = new MyList[String]

  // generic methods

  object MyList {
    def empty[A]: MyList[A] = ??? //nothing
  }

  val emptyListOfIntegers = MyList.empty[Int]

  //---

  // variance problem
  class Animal

  class Dog extends Animal

  class Cat extends Animal

  // 1. COVARIANCE - List[Cat] extends List[Animal]

  class CovariantList[+A] {
    //def add(element: A): Unit
  }
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]

  //animalList.add(new Dog) ???

  val myListOfCats: MyList[Cat] = new MyList[Cat];
  val myListOfAnimals: MyList[Animal] = new MyList[Cat];

  myListOfCats.add(new Animal)
  myListOfCats.add(new Cat)
  myListOfCats.add(new Dog)



  // 2. INVARIANCE

  class InvariantList[A]
  //var invariantListOfAnimal: InvariantList[Animal] = new InvariantList[Cat]
  var invariantListOfAnimal: InvariantList[Animal] = new InvariantList[Animal]

  // 3. Hel no! CONTROVARIANCE

  class ControvatiantList[-A]
  val convariantList: ControvatiantList[Cat] = new ControvatiantList[Animal]

  //---

  // bounded types

  class Cage[A <: Animal](animal: Animal)

  val cage: Cage[Animal] = new Cage[Animal](new Dog);
  val cage2 = new Cage(new Dog);






}
