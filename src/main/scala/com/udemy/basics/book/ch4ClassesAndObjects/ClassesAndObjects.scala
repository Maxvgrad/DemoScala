package com.udemy.basics.book.ch4ClassesAndObjects

object ClassesAndObjects extends App {

}

class ChecksumAccumulator {
  private var sum = 0

  def add(b: Int) { // without equals sign return type is Unit
    //b = 3 // b is val
    sum += b
  }
}
