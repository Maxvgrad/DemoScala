package com.udemy.basics.exercises

abstract class BaseMaybe[+T] {

  def element: T

  def map[C](f: T => C): BaseMaybe[C]

  def filter(f: T => Boolean): BaseMaybe[T]

  def flatMap[C](f: T => BaseMaybe[C]): BaseMaybe[C]

}

object AnEmpty extends BaseMaybe[Nothing] {

  override def element: Nothing = throw new UnsupportedOperationException

  override def map[C](f: Nothing => C): BaseMaybe[C] = AnEmpty

  override def filter(f: Nothing => Boolean): BaseMaybe[Nothing] = AnEmpty

  override def flatMap[C](f: Nothing => BaseMaybe[C]): BaseMaybe[C] = AnEmpty
}

case class Maybe[+T](element: T) extends BaseMaybe[T] {

  override def map[C](f: T => C): BaseMaybe[C] =
    Maybe(f(element))

  override def filter(f: T => Boolean): BaseMaybe[T] =
    if (f(element)) this
    else AnEmpty

  override def flatMap[C](f: T => BaseMaybe[C]): BaseMaybe[C] = f(element)

}
