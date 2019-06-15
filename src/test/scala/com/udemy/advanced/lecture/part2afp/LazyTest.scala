package com.udemy.advanced.lecture.part2afp

import org.scalatest.FlatSpec

class LazyTest extends FlatSpec {

  behavior of "LazyTest"

  it should "lazily evaluate the monad content" in {
    // given
    val lazzy: Lazy[Int] = Lazy {
      println("lazzzzzy!")
      5
    }
    println("lazzy is created")

    println(lazzy.flatMap(Lazy(_)))
    println(lazzy.flatMap(Lazy(_)))
  }


  it should "left identity" in {
    def f(n: Int): Lazy[Int] = Lazy(n % 5)
    val x = 5

    //unit(x).flatMap(f) == f(x)

    assertResult(Lazy(x).flatMap(x => f(x)))(f(x))
  }

  it should "right identity" in {
    val monad: Lazy[Int] = Lazy(100)

    //monad.flatMap(unit) == monad
    assertResult(monad.flatMap(Lazy(_)))(monad)
  }

  it should "associativity" in {
    def f(n: Int): Lazy[Int] = Lazy(n * n)
    def g(n: Int): Lazy[Int] = Lazy(n / n)
    val monad = Lazy(500)

    //monad.flatMap(f).flatMap(g) == monad.flatMap(x => f(x).flatMap(g))
    assertResult(monad.flatMap(x => f(x)).flatMap(x => g(x)))(monad.flatMap(x => f(x).flatMap(e => g(e))))
  }

}
