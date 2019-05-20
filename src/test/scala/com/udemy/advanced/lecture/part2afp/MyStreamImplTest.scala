package com.udemy.advanced.lecture.part2afp

import org.scalatest.FlatSpec

import scala.collection.mutable.ListBuffer

class MyStreamImplTest extends FlatSpec {

  behavior of "MyStreamImplTest"

  it should "head as expected" in {
    val stream: MyStream[Int] = MyStream.from(100)(s => s + 2)
    assertResult(100)(stream.head)
  }

  it should "foreach print only numbers which is power of two" in {
    // given
    val stream: MyStream[Int] = MyStream.from(2)(s => s + 2)
    val acc: ListBuffer[Int] = new ListBuffer[Int]()
    // when
    stream.take(100).foreach(s => if (powOfTwo(s)) acc += s)
    // then
    assertResult(List(2, 4, 8, 16, 32, 64, 128))(acc.toList)
  }

  it should "takeAsList" in {
    // given
    val stream: MyStream[Int] = MyStream.from(2)(s => s + 2)
    val acc: ListBuffer[Int] = new ListBuffer[Int]()
    // when - then
    assertResult(List(2, 4, 8, 16, 32, 64, 128))(stream.filter(s => powOfTwo(s)).takeAsList(7))
  }

  it should "map" in {
    val stream: MyStream[Int] = MyStream.from(1)(s => s + 2)
    assertResult(10)(stream.map(_ * 10).head)
  }

  it should "tail" in {
    val stream: MyStream[Int] = MyStream.from(1)(s => s + 1)
    assertResult(2)(stream.tail.head)
  }

  it should "isEmpty" in {
    val stream: MyStream[Int] = MyStream.from(1)(s => s + 1)
    assertResult(false)(stream.isEmpty)
  }

  it should "filter" in {
    val stream: MyStream[Int] = MyStream.from(100)(s => s + 2)
    //assertResult(List(128, 256))(stream.filter(powOfTwo).takeAsList(200))
  }

  it should "prepend" in {
    val stream: MyStream[Int] = MyStream.from(1)(s => s + 2)
    assertResult(30)((30 #:: stream) head)
  }

  it should "append" in {
    val stream: MyStream[Int] = MyStream.from(1)(s => s + 2)
    assertResult(1)((stream ++ MyStream.from(2)(s => s + 2)) head)
  }

  it should "flatMap" in {
    val stream: MyStream[Int] = MyStream.from(2)(s => s + 2)
    assertResult(List(2, 2, 4, 4, 6, 6, 8, 8, 10, 10)) {
      stream.flatMap(x => new Cons[Int](x, new Cons[Int](x, Empty))).take(10).toList()
    }
  }

  it should "take expect 100 iterations" in {
    // given
    val stream: MyStream[Int] = MyStream.from(2)(s => s + 1)
    var count: Int = 0
    // when
    stream.take(100).foreach(s => count += 1)
    // then
    assertResult(100)(count)
  }

  it should "fibonacci numbers stream" in {
    // given
    val fibonacciStream: MyStream[Int] = MyStream.fibonacci(1)(1)
    // when - then
    assertResult(List(1, 1, 2, 3, 5, 8, 13, 21, 34, 55))(fibonacciStream.take(10).toList())
  }

  it should "be eratosthenes's scive prime numbers" in {
    val numbers: MyStream[Int] = MyStream.from(2)(s => s + 1)
    assertResult(List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29))(MyStream.eratosthenes(numbers).take(10).toList())
  }

  val powOfTwo = (s: Int) => Math.ceil(Math.log(s) / Math.log(2)) == Math.floor(Math.log(s) / Math.log(2))
}
