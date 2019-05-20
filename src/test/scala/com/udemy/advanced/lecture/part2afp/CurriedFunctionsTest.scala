package com.udemy.advanced.lecture.part2afp

import java.time.Instant

import org.scalatest.FlatSpec

class CurriedFunctionsTest extends FlatSpec {

  behavior of "CurriedFunctionsTest"

  it should "curriedMethod" in {
    val curriedAdder4: Int => Int = new CurriedFunctions().curriedMethod(4) // ETA - Expansion = lifting

    assertResult(10)(curriedAdder4(6))
  }


  it should "curriedMethod ETA expansion" in {
    val curriedAdder50= new CurriedFunctions().curriedMethod(50) _ // <<<<

    assertResult(60)(curriedAdder50(10))
  }

  it should "simpleAddFunction implement add7" in {
    val add7_1 = new CurriedFunctions().simpleAddFunction(7, _: Int)
    val add7_2 = new CurriedFunctions().simpleAddFunction(_: Int, 7)
    val add7_3 = (x: Int) => new CurriedFunctions().simpleAddFunction(x, 7)
    val add7_4 = new CurriedFunctions().simpleAddFunction.curried(7)

    assertResult(10)(add7_1(3))
    assertResult(10)(add7_2(3))
    assertResult(10)(add7_3(3))
    assertResult(10)(add7_4(3))
  }

  it should "simpleAddMethod implement add7" in {
    val add7_1 = new CurriedFunctions().simpleAddMethod(7, _: Int)
    val add7_2 = new CurriedFunctions().simpleAddMethod(_: Int, 7)
    val add7_3 = (x: Int) => new CurriedFunctions().simpleAddMethod(x, 7)

    assertResult(10)(add7_1(3))
    assertResult(10)(add7_2(3))
    assertResult(10)(add7_3(3))
  }

  it should "curriedAddMethod implement add7" in {
    val add7_1 = new CurriedFunctions().curriedAddMethod(7) _ // partially applied function
    val add7_3 = (x: Int) => new CurriedFunctions().curriedAddMethod(x)(7)

    assertResult(10)(add7_1(3))
    assertResult(10)(add7_3(3))
  }

  it should "numberFormatter" in {

    val list = List(Math.PI, Math.E, 323232.3243242543)

    assertResult(true)(list.map(new CurriedFunctions().format4_2f).contains("3,14"))
    assertResult(true)(list.map(new CurriedFunctions().format8_6f).contains("2,718282"))
    assertResult(true)(list.map(new CurriedFunctions().format14_12f).contains("3,141592653590"))
  }

  it should "Methods as functions (or, what exactly is “eta expansion”?" in {


    def byName(n: => Int) = n + 1
    def byFunction(f: () => Int) = f() + 1

    def method: Int = 42
    def parentMethod(): Int = 42

    println(byName(40))
    println(byName(method))
    println(byName(parentMethod()))
    println(byName(parentMethod))
    println(((x: Int) => byName(x))(43))

    println(byFunction(() => 40))
    //println(byFunction(method)) !!!!
    println(byFunction(method _ ))
    println(byFunction(parentMethod _ ))
    println(((x: Int) => byFunction(() => x))(43))


    def method3: Instant = Instant.now

    def method2(): Instant = Instant.now
    assertResult(43)(byName(method))
    assertResult(43)(byName(method))
    assertResult(43)(byName(parentMethod()))
    assertResult(43)(byName(parentMethod))

    println("method2 = " + method2())
    println("method2 = " + method2)
    println("method3 = " + method3)
    println("method3 = " + method3)

  }

}
