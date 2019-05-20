package com.udemy.advanced.lecture.part2afp

import org.scalatest.FlatSpec

class PartialFunctionsTest extends FlatSpec {

  "PartialFunction" should "defined" in {

    val pFunction: PartialFunction[Int, Int] = {
      case 4 => 33
    }

    assertResult(true){
      pFunction.isDefinedAt(4)
    }
  }

  "PartialFunction" should "lift" in {

    val pFunction: PartialFunction[Int, Int] = {
      case 4 => 33
    }

    val pLiftedFunction = pFunction.lift

    assertResult(Some(33)){
      pLiftedFunction(4)
    }
  }

  "PartialFunction" should "or else" in {
    val pFunction: PartialFunction[Int, Int] = {
      case 4 => 33
    }

    val pfChain = pFunction.orElse[Int, Int] {
      case 5 => 50
    }

    assertResult(50){
      pfChain(5)
    }
  }

  "PartialFunction" should "work like a charm with HOF" in {
    assertResult(List(40, 50, 60)){
      List(1, 2, 3).map {
        case 1 => 40
        case 2 => 50
        case 3 => 60
      }
    }
  }

  "MyPartialFunction" should "work like a charm" in {
    val pFunction: MyPartialFunction[Int, Int] = {
      case 1 => 1
      case 2 => 1
      case 3 => 2
      case 4 => 3
      case 5 => 5
    }

    assertResult(true){
      pFunction.isDefined(1)
    }

    assertResult(false){
      pFunction.isDefined(6)
    }

    assertResult(5){
      pFunction(5)
    }

    assertResult(None){
      pFunction.lift(6)
    }

  }

}
