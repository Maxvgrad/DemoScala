package com.udemy.basics.lectures.part3fp

import org.scalatest.FlatSpec

import scala.collection.convert.Wrappers.SeqWrapper
import scala.collection.mutable

class SequencesTests extends FlatSpec {

  "List " should " head is 42 after prepending ::" in {
    val list = 42 :: List(1, 2, 3)
    assertResult(42) {
      list.head
    }
  }

  "List " should " head is 42 after prepending +:" in {
    val list = 42 +: List(1, 2, 3)
    assertResult(42) {
      list.head
    }
  }

  "List " should " last is 42 after appending :+" in {
    val list = List(1, 2, 3) :+ 42
    assertResult(42) {
      list.last
    }
  }

  "After List.fill " should " contain 5 apples" in {
    val list = List.fill(5)("apple")
    assertResult(true) {
      list.forall(_ == "apple")
    }
  }

  "After List.mkString " should " equals" in {
    val list = List(1,2)
    assertResult("Apples: 1,2.") {
      list.mkString("Apples: ", ",", ".")
    }
  }

  "Array " should " contain element number 3" in {
    val array = Array(1,2)
    array(1) = 3
    assertResult(true) {
      array.contains(3)
    }
  }

  "Insertion " should " be faster in vector then list" ignore { //long running test
    //given
    val sequences = Sequences
    val maxCapacitry = 1000
    val maxRuns = 100
    val list = (1 to maxCapacitry).toList
    val vector = (1 to maxCapacitry).toVector

    assertResult(true) {
      val averageTimeForList = sequences.getWriteTime(list, maxRuns, maxCapacitry)
      val averageTimeForVector = sequences.getWriteTime(vector, maxRuns, maxCapacitry)

      println(s"List time is: $averageTimeForList")
      println(s"Vector time is: $averageTimeForVector")

      averageTimeForList > averageTimeForVector
    }
  }



}
