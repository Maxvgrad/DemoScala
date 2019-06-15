package com.udemy.advanced.lecture.part2afp

import org.scalatest.FlatSpec

class AttemptTest extends FlatSpec {

  behavior of "monads"

  it should "left identity" in {
    def f(n: Int): Attempt[Int] = Attempt(n % 5)
    val x = 5

    //unit(x).flatMap(f) == f(x)

    assertResult(Attempt(x).flatMap(f))(f(x))
  }

  it should "right identity" in {
    val monad: Attempt[Int] = Attempt(100)

    //monad.flatMap(unit) == monad
    assertResult(monad.flatMap(Attempt(_)))(monad)
  }

  it should "associativity" in {
    def f(n: Int): Attempt[Int] = Attempt(n * n)
    def g(n: Int): Attempt[Int] = Attempt(n / n)
    val monad = Attempt(500)

    //monad.flatMap(f).flatMap(g) == monad.flatMap(x => f(x).flatMap(g))
    assertResult(monad.flatMap(f).flatMap(g))(monad.flatMap(x => f(x).flatMap(g)))
  }


}
