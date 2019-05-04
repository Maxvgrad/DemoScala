package com.udemy.basics.exercises

abstract class MyList[+A]  {

  def head: A

  def tail: MyList[A]

  def add[B >: A](element: B): MyList[B]

  def isEmpty(): Boolean

  // higher - order functions
  def filter(predicate: A => Boolean): MyList[A]
  def map[E](function: A => E): MyList[E]
  def flatMap[E](function: A => MyList[E]): MyList[E]

  //hofs
  def foreach[E](function: A => Unit): Unit
  def sort(function: (A, A) => Int): MyList[A]
  def zipWith[B, C](list: MyList[B], function: (A, B) => C): MyList[C]
  def fold[B](acc: B)(function: (A, B) => B): B //reduce

  def printElements(): String

  def ++[B >: A](list: MyList[B]): MyList[B]

  override def toString: String = "[" + printElements + "]"
}

case object Empty extends MyList[Nothing] {

  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyList[Nothing] = throw new NoSuchElementException

  override def add[B >: Nothing](element: B): MyList[B] = Cons[B](element, Empty)

  override def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  override def map[E](function: Nothing => E): MyList[E] = Empty

  override def flatMap[E](function: Nothing => MyList[E]): MyList[E] = Empty

  override def foreach[E](function: Nothing => Unit): Unit = ()

  override def sort(function: (Nothing, Nothing) => Int): MyList[Nothing] = Empty

  override def zipWith[B, C](list: MyList[B], function: (Nothing, B) => C): MyList[C] =
    if (!list.isEmpty()) throw new IllegalArgumentException("Sizes not eq!")
    else Empty

  override def fold[B](acc: B)(function: (Nothing, B) => B): B = acc

  override def isEmpty(): Boolean = true

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  override def printElements(): String = ""
}

case class Cons[+A](head: A, tail: MyList[A]) extends MyList[A] {

  override def add[B >: A](element: B): MyList[B] = Cons[B](element, this)

  override def filter(predicate: A => Boolean): MyList[A] =
    if (predicate(head))
      Cons[A](head, tail.filter(predicate))
    else
      tail.filter(predicate)

  override def map[E](function: A => E): MyList[E] =
    Cons[E](function(head), tail.map(function))

  override def flatMap[E](function: A => MyList[E]): MyList[E] = {
    function(head) ++ tail.flatMap(function)
  }

  override def foreach[E](function: A => Unit): Unit = {
      function(head)
      tail.foreach(function)
  }

  override def sort(comparator: (A, A) => Int): MyList[A] = {
    def insert(e: A, list: MyList[A]): MyList[A] = {
      if (list.isEmpty()) Cons(e, Empty)
      else if (comparator(e, list.head) <= 0) Cons(e, list)
      else Cons(list.head, insert(e, list.tail))
    }
    val sortedTail = tail.sort(comparator)
    insert(head, sortedTail)
  }

  override def zipWith[B, C](list: MyList[B], function: (A, B) => C): MyList[C] = {
    if (list.isEmpty()) throw new IllegalArgumentException("Sizes not eq!")
    else Cons[C](function(this.head, list.head), this.tail.zipWith(list.tail, function))
  }

  override def fold[B](acc: B)(function: (A, B) => B): B = {
    if (isEmpty()) acc
    else tail.fold(function(head, acc))(function)
  }

  override def isEmpty(): Boolean = false

  override def ++[B >: A](list: MyList[B]): MyList[B] = Cons[B](head, tail ++ list)

  override def printElements(): String = head.toString + ", " + tail.printElements
}

object ListTest extends App {
  val list = Cons(1, Cons(5, Cons(2, Empty)))
  println(list.tail.head)

  println(list.toString)

  class Dog {
    override def toString: String = "Dog"
  }

  val genericList: Cons[Dog] = Cons(new Dog, Cons(new Dog, Cons[Dog](new Dog, Empty)))
  println(genericList.toString)

  // filter test

  val listOfInts: Cons[Int] = Cons(4, Cons[Int](2, Cons[Int](8, Cons[Int](9, Cons[Int](16, Empty)))))

  val listOfEvenInts = listOfInts.filter(i => i % 2 == 0)

  println(listOfEvenInts.toString)

  val listOfString: Cons[String] = Cons[String]("Hello", Cons[String]("Max", Cons[String]("!", Empty)))

  val listOfStringsLength = listOfString map(s => s.length)

  println(listOfStringsLength toString)

  println(listOfInts flatMap(i => Cons[Int](i, Cons[Int](i+1, Empty))))


  //Fold test
  val numbers: Cons[Int] = Cons(2, Cons[Int](4, Cons[Int](6, Cons[Int](12, Cons[Int](24, Empty)))))
  val numbers2: Cons[Int] = Cons(1, Cons[Int](2, Cons[Int](3, Cons[Int](12, Cons[Int](24, Empty)))))

  println("fold test")
  println(numbers.fold(0)((a, b) => a + b))
  println(numbers2.fold(0)(_ + _))

  println("forEach test")
  numbers.foreach(element => println(s"Element: $element"))
  numbers.foreach(println)

  println("zip test")
  val zippedNumbers: MyList[Int] = numbers.zipWith(numbers2, (h1: Int, h2: Int) => h1 + h2)
  zippedNumbers.foreach(element => println(s"Element: $element"))
  numbers.zipWith[Int, String](numbers2, _ + " " + _).foreach(println)


  println("sort test")
  val unordered: Cons[Int] = Cons(44, Cons[Int](19, Cons[Int](20, Cons[Int](12, Cons[Int](5, Empty)))))
  unordered.sort((x: Int, y: Int) => x - y).foreach(println)


  // check for comprehensions

  println("for-comprehensions")

  for {
    n <- numbers
  } print(n)

  println("  even:")

  val combinations = for {
    n <- numbers
    s <- listOfString
  } yield s + " - " + n

  println(combinations)

}
