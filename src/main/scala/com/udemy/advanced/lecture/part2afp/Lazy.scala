package com.udemy.advanced.lecture.part2afp

class Lazy[+A](value: => A) {

  // call by need
  private lazy val valueInternal = value

  def flatMap[B](f: (=> A) => Lazy[B]): Lazy[B] = f(valueInternal)

  def map[B](f: A => B): Lazy[B] = flatMap(x => Lazy(f(x)))

  def flatten[B](m: => Lazy[Lazy[B]]): Lazy[B] = m.flatMap(x => x)

  def get: A = valueInternal
}

object Lazy {
  def apply[A](f: => A): Lazy[A] = new Lazy[A](f)
}