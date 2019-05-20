package com.udemy.advanced.lecture.part2afp

import scala.annotation.tailrec

abstract class MyStream[+A] {

  def isEmpty: Boolean

  def head: A

  def tail: MyStream[A]

  /** Prepend
    */
  def #::[B >: A](element: B): MyStream[B]

  /** Concatenate two streams
    */
  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B]

  def foreach(f: A => Unit)

  def map[C](f: A => C): MyStream[C]

  def flatMap[C](f: A => MyStream[C]): MyStream[C]

  def filter(f: A => Boolean): MyStream[A]

  /** Take first n elements from stream
    */
  def take(n: Int): MyStream[A]
  def takeAsList(n: Int): List[A]

  @tailrec
  final def toList[B >: A](acc: List[B] = Nil): List[B] =
    if(isEmpty) acc.reverse
    else tail.toList(head :: acc)
}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] =
    new Cons[A](start, from(generator(start))(generator))

  def fibonacciRecursive(start: Int): MyStream[Int] = {

    def fibonacciGenerator(n: Int): Int = {
      if (n == 1 || n == 2) 1
      else fibonacciGenerator(n - 1) + fibonacciGenerator(n - 2)
    }
    new Cons[Int](fibonacciGenerator(start), fibonacciRecursive(start + 1))
  }

  def fibonacci(first: Int)(second: Int): MyStream[Int] = {
    new Cons[Int](first, fibonacci(second)(first + second))
  }

  def eratosthenes(numbers: MyStream[Int]): MyStream[Int] = {
    if (numbers.isEmpty) numbers
    else new Cons[Int](numbers.head, eratosthenes(numbers.tail.filter(_ % numbers.head != 0)))
  }
}

object Empty extends MyStream[Nothing] {

  override def isEmpty: Boolean = true

  override def head: Nothing = throw new IllegalArgumentException

  override def tail: MyStream[Nothing] = throw new IllegalArgumentException

  /** Prepend
    */
  override def #::[B >: Nothing](element: B): MyStream[B] = new Cons[B](element, this)

  /** Concatenate two streams
    */
  override def ++[B >: Nothing](anotherStream: => MyStream[B]): MyStream[B] = anotherStream

  override def foreach(f: Nothing => Unit): Unit = ()

  override def map[C](f: Nothing => C): MyStream[C] = this

  override def flatMap[C](f: Nothing => MyStream[C]): MyStream[C] = this

  override def filter(f: Nothing => Boolean): MyStream[Nothing] = this

  /** Take first n elements from stream
    */
  override def take(n: Int): MyStream[Nothing] = this

  override def takeAsList(n: Int): List[Nothing] = Nil
}

private class Cons[A](override val head: A, generator: => MyStream[A]) extends MyStream[A] {

  override def isEmpty: Boolean = false

  override lazy val tail: MyStream[A] = generator // call by need

  /** Prepend
    */
  override def #::[B >: A](element: B): MyStream[B] = new Cons(element, this)

  /** Concatenate two streams
    */
  override def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = new Cons(head, tail ++ anotherStream)

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  override def map[C](f: A => C): MyStream[C] = new Cons[C](f(head), tail.map(f))

  override def flatMap[C](f: A => MyStream[C]): MyStream[C] = f(head) ++ tail.flatMap(f)

  override def filter(f: A => Boolean): MyStream[A] =
    if (f(head)) new Cons(head, tail.filter(f))
    else tail.filter(f)

  /** Take first n elements from stream
    */
  override def take(n: Int): MyStream[A] =
    if (tail.isEmpty) Empty
    else if (n == 1) new Cons(head, Empty)
    else new Cons(head, tail.take(n - 1))

  override def takeAsList(n: Int): List[A] = take(n).toList()
}
