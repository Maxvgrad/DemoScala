package com.udemy.advanced.lecture


object AdvancedPatternMatching extends App {

  val numbers = List(2)
  val aNumbersMatch = numbers match {
    case head :: Nil => println(s"The only element is $head") // how does it work
    case _ =>
  }

  class Person(val name: String, val age: Int)
  
  object Person {
    def unapply(arg: Person): Option[(String, Int)] = Some((arg.name, arg.age))
    def unapply(age: Int): Option[String] = Some(if (age < 25) "minor" else "major")
  }

  val bob = new Person("Bob", 25)

  val patternMatching = bob match {
    case Person(n, a) => println(s"Name: $n, age: $a")
  }

  val ageMatching = bob.age match {
    case Person(status) => s"My legal status is $status"
  }

  // Exercise

  object even {
    def unapply(n: Int): Option[Boolean] = Option(n % 2 == 0)
  }

  object singleDigit {
    def unapply(n: Int): Option[Boolean] = Option(n > -10 && n < 10)
  }

  def matchDigit(n: Int) = n match {
    case singleDigit(result) => "Single digit " + result
    case even(result) => "Even " + result
    case _ =>
  }

  println(matchDigit(10))
  println(matchDigit(9))


  // infix patterns

  case class Or[A, B](a: A, b: B)

  val either = Or("Four", 4)

  def matching(element: Or[String, Int]) = element match {
    case number Or string => s"$number = $string"
  }

  println(matching(either))


  // decomposing sequences

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list.equals(Empty)) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }

  val myList: MyList[Int] = new Cons[Int](1, new Cons[Int](4, new Cons[Int](5, Empty)))

  def decomposeMyList(list: MyList[Int]) = list match {
    case MyList(1, 2, _*) => "starts with 1 2"
    case _ => "does not match"
  }

  println(decomposeMyList(new Cons[Int](1, new Cons[Int](4, new Cons[Int](5, Empty)))))
  println(decomposeMyList(new Cons[Int](1, new Cons[Int](2, new Cons[Int](5, Empty)))))


  // custom return types for unapply
  // isEmpty: Boolean, get: something


  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false
      override def get: String = person.name
    }
  }

  println(new Person("Jon", 22) match {
    case PersonWrapper(n) => s"This person name is $n"
    case _ => "do not match"
  })

}
