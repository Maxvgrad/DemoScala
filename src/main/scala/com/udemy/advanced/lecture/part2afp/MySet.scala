package com.udemy.advanced.lecture.part2afp

import scala.annotation.tailrec

trait MySet[A] extends (A => Boolean) {

  override def apply(v1: A): Boolean = contains(v1)

  def contains(elem: A): Boolean

  def size: Int
  def isEmpty(): Boolean
  def +(elem: A): MySet[A]
  def -(elem: A): MySet[A]
  def ++(set: MySet[A]): MySet[A] //union
  def &(set: MySet[A]): MySet[A] //intersection
  def --(set: MySet[A]): MySet[A] //diff

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(f: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit

  def unary_! :MySet[A]

}

class EmptySet[A] extends MySet[A] {

  override def size: Int = 0

  override def isEmpty(): Boolean = true

  override def contains(elem: A): Boolean = false

  override def +(elem: A): MySet[A] = LinkedSet(elem, this)

  override def -(elem: A): MySet[A] = this

  override def ++(set: MySet[A]): MySet[A] = set

  override def &(set: MySet[A]): MySet[A] = new EmptySet[A]

  override def --(set: MySet[A]): MySet[A] = this

  override def map[B](f: A => B): MySet[B] = new EmptySet[B]

  override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]

  override def filter(f: A => Boolean): MySet[A] = this

  override def foreach(f: A => Unit): Unit = Unit

  override def unary_! : MySet[A] = new PropertyBaseSet[A](_ => true)
}

case class LinkedSet[A](head: A, tail: MySet[A]) extends MySet[A] {

  override def isEmpty(): Boolean = false

  override def size: Int = 1 + tail.size

  override def contains(elem: A): Boolean =
    head.equals(elem) || tail.contains(elem)

  override def +(elem: A): MySet[A] =
    if(contains(elem)) this
    else LinkedSet(elem, this)

  override def -(elem: A): MySet[A] =
    if(head == elem) tail
    else (tail - elem) + head

  override def ++(set: MySet[A]): MySet[A] = tail ++ set + head

  override def &(set: MySet[A]): MySet[A] = filter(set)

  override def --(set: MySet[A]): MySet[A] = filter(x => !set(x))

  override def map[B](f: A => B): MySet[B] = tail.map(f) + f(head)

  override def flatMap[B](f: A => MySet[B]): MySet[B] = tail.flatMap(f) ++ f(head)

  override def filter(f: A => Boolean): MySet[A] =
    if (f(head)) tail.filter(f) + head
    else tail.filter(f)

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  override def unary_! : MySet[A] = new PropertyBaseSet[A](x => !contains(x))
}

object MySet {
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(seq: Seq[A], acc: MySet[A]): MySet[A] =
      if(seq.isEmpty) acc
      else buildSet(seq.tail, acc + seq.head)
    buildSet(values.toSeq, new EmptySet[A])
  }
}

class PropertyBaseSet[A](property: A => Boolean) extends MySet[A] {

  override def contains(elem: A): Boolean = property(elem)

  override def size: Int = ???

  override def isEmpty(): Boolean = ???

  override def +(elem: A): MySet[A] = new PropertyBaseSet[A](x => property(x) || x == elem)

  override def -(elem: A): MySet[A] = filter(_ != elem)

  override def ++(set: MySet[A]): MySet[A] = new PropertyBaseSet[A](x => property(x) || set(x))

  override def &(set: MySet[A]): MySet[A] = filter(set)

  override def --(set: MySet[A]): MySet[A] = filter(!set)

  override def map[B](f: A => B): MySet[B] = politeFailure

  override def flatMap[B](f: A => MySet[B]): MySet[B] = politeFailure

  override def filter(f: A => Boolean): MySet[A] = new PropertyBaseSet[A](x => f(x) && property(x))

  override def foreach(f: A => Unit): Unit = politeFailure

  override def unary_! : MySet[A] = new PropertyBaseSet[A](x => !property(x))

  def politeFailure = throw new IllegalArgumentException("Unsupported")
}


