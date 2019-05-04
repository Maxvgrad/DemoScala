package com.udemy.basics.lectures.part3fp

import scala.util.Random

object Sequences extends App {

  // Seq

  val aSequence = Seq(1,2,3,4)

  println(aSequence)
  println(aSequence.reverse)
  println(aSequence(2))
  println(aSequence ++ Seq(5,6,7))

  println(Seq(1,0,5).sorted)

  // Range

  val aRange: Seq[Int] = 1 to 10
  println(aRange.foreach(print))

  println()

  val aRangeUntil: Seq[Int] = 1 until 10
  println(aRangeUntil.foreach(print))

  // ten times hello

  (1 to 10).foreach(i => print(" hello"))

  // lists
  val aList = List(1, 2, 3)
  val prepended = 42 :: aList


  def getWriteTime(collection: Seq[Int], maxRuns: Int, maxCapacity: Int): Double = {
    val r = new Random()

    val times = for {
      it <- 1 to maxRuns
    } yield {
      val t1 = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt())

      System.nanoTime() - t1
    }

    times.sum * 1.0 / maxRuns
  }


}
