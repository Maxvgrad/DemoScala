package com.udemy.advanced.lecture.part2afp

import org.scalatest.FlatSpec

class PropertyBaseSetTest extends FlatSpec {

  def fixture = new {
    val set123 = MySet(1, 2, 3)
    val exceptSet123 = new PropertyBaseSet[Int](x => !set123(x))
  }

  behavior of "PropertyBaseSetTest"

  it should "+" in {
    val sets = fixture

    assertResult(true)((sets.exceptSet123 + 3)(3))
    assertResult(true)((sets.exceptSet123 + 0)(0))
    assertResult(false)((sets.exceptSet123 + 2 + 3)(1))
  }

  it should "++" in {
    val sets = fixture

    assertResult(true)((sets.exceptSet123 ++ MySet(3))(3))
    assertResult(true)((sets.exceptSet123 ++ MySet(0))(0))
    assertResult(false)((sets.exceptSet123 ++ MySet(2, 3))(1))
  }


  it should "-" in {
    val sets = fixture

    assertResult(false)((sets.exceptSet123 - 3)(3))
    assertResult(false)((sets.exceptSet123 - -4)(-4))
    assertResult(false)((sets.exceptSet123 - 4)(4))
    assertResult(false)((sets.exceptSet123 - 0)(0))
    assertResult(true)((sets.exceptSet123 - 2 - 3)(0))
  }

  it should "--" in {
    val sets = fixture

    assertResult(false)((sets.exceptSet123 -- MySet(-4, 4))(-4))
    assertResult(false)((sets.exceptSet123 -- MySet(-4, 4))(4))
    assertResult(false)((sets.exceptSet123 -- MySet(0))(0))
    assertResult(true)((sets.exceptSet123 -- MySet(2, 3))(0))
  }

  it should "filter" in {
    val sets = fixture

    assertResult(false)(sets.exceptSet123.filter(_ % 2 == 0)(9))
    assertResult(false)(sets.exceptSet123.filter(_ % 2 == 0)(2)) //2 not included before filter applied
    assertResult(true)(sets.exceptSet123.filter(_ % 2 != 0)(9))
  }

  it should "map" in {

  }

  it should "isEmpty" in {

  }

  it should "contains" in {
    val sets = fixture

    assertResult(true)(sets.exceptSet123(10))
    assertResult(false)(sets.exceptSet123(2))
  }

  it should "size" in {

  }

  it should "compose" in {

  }

  it should "toString" in {

  }

}
