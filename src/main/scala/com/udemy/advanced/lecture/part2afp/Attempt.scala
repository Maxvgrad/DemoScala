package com.udemy.advanced.lecture.part2afp

trait Attempt[+A] {
  def flatMap[B](a: A => Attempt[B]): Attempt[B]
}

object Attempt {
  def apply[A](a: => A): Attempt[A] =
    try {
      Success(a)
    } catch {
      case e: Throwable => Fail(e)
    }
}

case class Success[+A](value: A) extends Attempt[A] {
  override def flatMap[B](a: A => Attempt[B]): Attempt[B] =
    try {
      a(value)
    } catch {
      case e: Throwable => Fail(e)
    }
}

case class Fail(e: Throwable) extends Attempt[Nothing] {
  override def flatMap[B](a: Nothing => Attempt[B]): Attempt[B] = this
}