package com.udemy.advanced.lecture.part2afp

import org.scalatest.FlatSpec

class LinkedSetTest extends FlatSpec {

  def fixture = new {
    val emptyIntSet: MySet[Int] = new EmptySet[Int]
    val emptyStringSet: MySet[String] = new EmptySet[String]

    val intSet: MySet[Int] = LinkedSet(10, new EmptySet[Int])
    val intSet12345: MySet[Int] = LinkedSet(1, LinkedSet(2, LinkedSet(3, LinkedSet(4, LinkedSet(5, new EmptySet[Int])))))
    val stringSet: MySet[String] = LinkedSet("element1", new EmptySet[String])
  }

  behavior of "MySet"

  it should "contains false" in {
    val sets = fixture

    assertResult(false) {
      sets.stringSet("element2")
    }
  }

  it should "contains true" in {
    val sets = fixture

    assertResult(true) {
      sets.stringSet("element1")
    }
  }

  it should "add unique value" in {
    val sets = fixture

    assertResult(1) {
      (sets.emptyIntSet + 4).size
    }
  }

  it should "not add a duplicate value" in {
    val sets = fixture

    assertResult(1) {
      (sets.intSet + 10).size
    }
  }

  it should "union set" in {
    val sets = fixture

    assertResult(2) {
      (sets.intSet ++ LinkedSet(20, new EmptySet[Int])).size
    }
  }

  it should "not union set in case duplicates" in {
    val sets = fixture

    assertResult(1) {
      (sets.intSet ++ sets.intSet).size
    }

    assertResult(1) {
      (sets.intSet ++ sets.emptyIntSet).size
    }
  }

  it should "foreach" in {
    val sets = fixture
    sets.intSet12345.foreach(println)
  }

  it should "map function produce a constant" in {
    val sets = fixture

    assertResult(1) {
      sets.intSet12345.map((x:Int) => "const").size
    }
  }

  it should "map" in {
    val sets = fixture

    assertResult(5) {
      sets.intSet12345.map(_.toString).size
    }
  }

  it should "isEmpty true" in {
    val sets = fixture

    assertResult(false) {
      sets.stringSet.isEmpty()
    }
  }

  it should "isEmpty false" in {
    val sets = fixture

    assertResult(true) {
      sets.emptyIntSet.isEmpty()
    }
  }

  it should "flatMap" in {
    val sets = fixture

    assertResult(10) {
      sets.intSet.flatMap((x:Int) => {
        def linker(n: Int, acc: MySet[Int]): MySet[Int] =
          if(n == 0) acc
          else linker(n-1, acc + n)
        linker(Math.abs(x), new EmptySet[Int])
      }).size
    }
  }

  it should "filter" in {
    val sets = fixture

    assertResult(2) {
      sets.intSet12345.filter(_ % 2 == 0).size
    }
  }

  it should "subtract existing element" in {
    val sets = fixture

    assertResult(false) {
      (sets.intSet12345 - 3).contains(3)
    }
  }

  it should "subtract non-existing element" in {
    val sets = fixture

    assertResult(true) {
      (sets.intSet12345 - 6).equals(sets.intSet12345)
    }
  }

  it should "subtract set of elements" in {
    val sets = fixture

    assertResult(3) {
      (sets.intSet12345 & MySet(1,3,5,6)).size
    }
  }

  it should "diff" in {
    val sets = fixture
    assertResult(true) {
      (sets.intSet12345 -- MySet(1,3,5,6))(4)
    }
  }

  it should "unary_!" in {
    val sets = fixture
    assertResult(false) {
      !sets.intSet12345(4)
    }
  }
}