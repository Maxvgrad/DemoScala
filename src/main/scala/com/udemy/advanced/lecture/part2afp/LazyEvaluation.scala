package com.udemy.advanced.lecture.part2afp

object LazyEvaluation extends App {

  // lazy delays the evaluation of values
  lazy val x: Int = throw new RuntimeException

  lazy val print: Int = {
    println("line is printed once once while the value evaluated!")
    42
  }

  println(print)
  println(print)

  // examples of implications:

  def sideEffectCondition = {
    println("Booo")
    true
  }

  def simpleCondition = false

  println(if (simpleCondition && sideEffectCondition) "true" else "false")


  // in conjunction with call by name

  def byName(n: => Int): Int = n + n + n + 1
  def callByNeed(t: => Int): Int = {
    // CALL BY NEED
    lazy val n = t // evaluate once
    n + n + n + 1
  }
  def retrieveMagicNumber: Int = {
    println("waiting")
    Thread.sleep(1000)
    42
  }
  println(callByNeed(retrieveMagicNumber))

  // filtering with lazy vals
  def lessThen30(i: Int) = {
    println(s"$i is less then 30?")
    i < 30
  }

  def greaterThen30(i: Int) = {
    println(s"$i is greater then 20?")
    i >= 20
  }

  val numbers = List(1, 25, 40, 5, 23)
  val lt30AndGt20 = numbers.filter(lessThen30).filter(greaterThen30)

  println(lt30AndGt20)

  println(numbers.withFilter(lessThen30).withFilter(greaterThen30).foreach(println))

  println(for { // for - comprehensions use withFilter with guards
    a <- List(1, 2, 3) if a % 2 == 0
  } yield 1 + a)
}
