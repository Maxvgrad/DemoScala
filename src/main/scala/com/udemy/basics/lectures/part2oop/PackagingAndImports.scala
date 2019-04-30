package com.udemy.basics.lectures.part2oop

//import com.udemy.basics.playground._//{Cinderella, PrinceCharming}
import java.sql.Date
import java.util.{Date => UtilDate}

import com.udemy.basics.playground.{PrinceCharming, Cinderella => Princess}

object PackagingAndImports extends App {

  val writer = new Writer("Jharls", "Women", 1990)

  val cinderella = new com.udemy.basics.playground.Cinderella // fully qualified name

  // package object

  sayHello

  println(s"speed of light = $SPEED_OF_LIGHT m/s")

  //imports


  val prince = new PrinceCharming
  val cinderella2 = new Princess

  // aliasing
  val utilDate = new UtilDate
  val sqlDate = new Date(1)

  //Default imports
  // java.lang
  // scala - Function, Int, Nothing
  // scala.Predef -
}
