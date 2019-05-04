package com.udemy.basics.lectures.part4pmatching

import java.io.File

import org.scalatest.{Failed, FlatSpec, Outcome}

class FixtureTests extends FlatSpec {

  //get fixture

  val fixture = new {
    val builder = new StringBuilder
  }

  "Get-fixture" should "work like a charm" in {
    val f = fixture
  }

  // instantiating fixture-context

  trait Builder {
    val builder = new StringBuilder
  }

  "Fixture-context" should "work like a charm" in new Builder {
    // builder is hear
    builder.append("Hello")
  }

  // overriding with fixture
  override protected def withFixture(test: NoArgTest): Outcome = {
      super.withFixture(test) match {
        case failed: Failed =>
          val currDir = new File(".")
          val fileNames = currDir.list()
          info("Dir snapshot: " + fileNames.mkString(", "))
          failed
        case other => other
      }
    }
}
