package com.udemy.advanced.lecture.part2afp

class CurriedFunctions {

  // Method - is not instance of Function
  def curriedMethod(x: Int)(y: Int): Int = x + y

  val simpleAddFunction = (x: Int, y: Int) => x + y

  def simpleAddMethod(x: Int, y: Int) = x + y

  def curriedAddMethod(x: Int)(y: Int) = x + y

  def numberFormatter(pattern: String)(number: Double): String = pattern format number

  def format4_2f = numberFormatter("%4.2f") _

  def format8_6f= numberFormatter("%8.6f") _

  def format14_12f= numberFormatter("%14.12f") _

}
