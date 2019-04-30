package com.udemy.basics.lectures.part3fp

object WhatsAFunction extends App {

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  val stringToIntConverter = new Function1[String, Int] {
    override def apply(v1: String): Int = v1.toInt
  }

  println(stringToIntConverter("42"))

  val adder: ((Int, Int) => Int) = (a, b) => a + b

  println(adder(32, 1))

  val adder2 = adder(2, _)

  println(adder2(3))

  val concatinator = (s1: String, s2: String) => s1 + s2
  println(concatinator("Hello ", "world"))


  val specialFunction: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(outer: Int): Int => Int = new Function[Int, Int] {
      override def apply(inner: Int): Int = outer + inner
    }
  }

  // special adder as anonymous function
  val anonymousSpecialFunction: Int => Int => Int = a => b => a + b

  println(anonymousSpecialFunction(4)(6))


  println(specialFunction(2)(8)) //curried function

}

trait MyFunction[A, B] {
  def apply(element: A): B
}
