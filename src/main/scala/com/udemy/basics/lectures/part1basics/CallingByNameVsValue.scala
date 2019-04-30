package com.udemy.basics.lectures.part1basics

object CallingByNameVsValue extends App {

  def callByValue(x: Long): Unit = {
    println("by value: " + x)
    println("by value: " + x)
  }

  def callByName(x: => Long): Unit = {
    println("by name: " + x)
    println("by name: " + x)
  }

  callByName(System.nanoTime())
  callByValue(System.nanoTime())

  def timer(): Int = {
    def timerHelper(acumulator: Int): Int = {
      if (acumulator > 1000) acumulator
      else timerHelper(acumulator + 1)
    }
    timerHelper(1)
  }

  def printer(arg1: Int, arg2: => Int): Unit = {
    print(arg1)
  }

  printer(33, timer())

}
