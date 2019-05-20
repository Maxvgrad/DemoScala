package com.udemy.advanced.lecture.part2afp

import scala.util.Try

abstract class MyPartialFunction[A,B] {

  def apply(a: A): B

  def isDefined(a: A): Boolean = Try { apply(a) }.isSuccess

  def lift: MyPartialFunction[A, Option[B]] = new LiftedPartialFunction[A](this)

  private class LiftedPartialFunction[A](val mpf: MyPartialFunction[A, B]) extends MyPartialFunction[A, Option[B]] {
    override def apply(a: A): Option[B] =
      if(isDefined(a)) Some(mpf(a))
      else None
  }

}


