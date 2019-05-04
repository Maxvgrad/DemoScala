package com.udemy.advanced.lecture

import scala.util.{Random, Try}

object DarkSugars extends App {

  // #1 method with single arg

  def singleArgMethod(n: Int) = s"Received arg = $n"

  val result = singleArgMethod {
    if (new Random().nextInt() % 2 == 0) 2
    else 3
  }

  // examples

  val aTryInstance = Try {
    throw new RuntimeException
  }

  List(1,2,3).map(x => {
    x + 1
  })


  // #2 single abstract method

  trait Action {
    def act(x: Int): Int
  }

  val actionInstance: Action = x => x + 1

  // examples
  val aThread = new Thread(() => println("Sweet scala!"))

  abstract class AnAbstractType {
    def implemented: Int = 33
    def f(x: Int): Unit
  }

  val anAbstractType: AnAbstractType = (x: Int) => println("")

  // 3 the :: #:: methods are special

  val prepended = 2 :: List(3, 4)
  println(prepended)

  //The associativity of an operator is determined by the operator’s last character.
  //Operators ending in a colon ‘:’ are right-associative. All other operators are left-associative.

  class MyStream[A] {
    def -->: (value: Int): MyStream[A] = this
  }
  val myStream = 1 -->: 2 -->: new MyStream[Int]


  // 4 multi word method naming

  class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s" $gossip")
  }

  val teenGirl = new TeenGirl("Lilly")
  teenGirl `and then said` "Scala is Rock!"

  // 5 infix types

  class Composite[A, B]

  //val composite: Int Composite String = ???


  // 6 update method

  val arr = Array(1, 2, 3)

  arr(2) = 7 // rewritten method update(2, 7)
  // used in mutable collections


  // 7 setters for mutable collections
  class Mutable {
    private var internalMemeber = 0
    def member = internalMemeber //getter
    def member_=(value: Int) = internalMemeber = value //setter
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42

  println(aMutableContainer.member)

}
