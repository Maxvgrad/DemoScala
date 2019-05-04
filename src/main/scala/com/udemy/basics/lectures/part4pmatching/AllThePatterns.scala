package com.udemy.basics.lectures.part4pmatching

import com.udemy.basics.exercises.{Cons, Empty}
import com.udemy.basics.lectures.part2oop.Generics.MyList

object AllThePatterns extends App {

  // 1 - constants

  val x: Any = "Scala"

  x match {
    case 1 => "One"
    case "Scala" => "The Scala"
    case true => "true"
    case AllThePatterns => "Singleton object"
  }

  // 2 - match anything

  // 2.1 wildcard
  x match {
    case _ =>
  }

  // 2.2 variable
  val matchVariable = x match {
    case something => s"Hello $something"
  }

  println(matchVariable)

  // 3 tuples

  val aTupl = (2, 3)

  println(aTupl match {
    case (1, 1) =>
    case (something, 3) => s"How much"
  })

  val nestedTuple = (2, (5, 8))

  println(nestedTuple match {
    case (_, (5, v)) => s"match: (_, (5, $v ))"
  })

  // 4 case classes

  // 5 list pattern

  // 6 type
  val unknown: Any = 2

  val unknownMatch = unknown match {
    case list: List[Int] => //explicit type specifier
    case _ =>
  }

  // 7 name binding

  val nameBindingMatch = Cons(3, Empty) match {
    case notEmpty @ Cons(_, _) => //name binding
    case Cons(1, rest @ Cons(2, _)) => //name binding inside nested patterns
  }

  // 8 multi patterns
  //val multiPattern: MyList[Int] = Cons(2, Empty) match {
    //case Empty | Cons(0, _) =>
  //}

  // 9 if guard


  /**
    * Question
    */

  val numbers = List(1, 2, 3)
  val numbersMatch = numbers match {
    case listStrings: List[String] => "strings"
    case listNumbers: List[Int] => "ints"
    case _ => "any"
  }

  println(numbersMatch)
  // JVM trick question

}
