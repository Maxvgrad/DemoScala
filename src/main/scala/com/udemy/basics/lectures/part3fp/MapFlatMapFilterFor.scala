package com.udemy.basics.lectures.part3fp

object MapFlatMapFilterFor extends App {

  val list = List(1,2,3)

  println(list.map(_ + 10))
  println(list.filter(_ % 2 == 0))

  println(list.flatMap((x: Int) => List(x, x+1)))

  val numbers = List(1,2,3,4)
  val chars = List("a","b","c","d")
  val colors = List("black","blue")

  // iteration
  println(chars.flatMap(c => numbers.flatMap(n => colors.map(c + n + " - " + _))))


  // for - comprehensions
  val forCombinations = for {
    n <- numbers if n % 2 == 0
    c <- chars
    color <- colors
  } yield c + n + "-" + color

  println(forCombinations)

  for {
    n <- numbers
  } println(n)

  // styntax overload

  list.map { x =>
    x * 2
  }.foreach(print)

}
