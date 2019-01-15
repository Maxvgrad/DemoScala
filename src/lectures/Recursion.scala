package lectures

import scala.annotation.tailrec

object Recursion extends App {



  def concat(str: String, times: Int): String = {
    @tailrec
    def concatHelper(accumulator: String, iteration: Int): String = {
      illegalArgumentHandler(iteration)
      if (iteration <= 1) accumulator
      else concatHelper(accumulator + str, iteration - 1)
    }
    concatHelper(str, times)
  }


  def fibonacciFunction(ofNumber: Int): Int = {
    illegalArgumentHandler(ofNumber)

    def fibonacciFunctionHelper(num: Int, accumulatorMinusTwo: Int, accumulatorMinusOne: Int): Int = {
      if (num == ofNumber) accumulatorMinusOne + accumulatorMinusTwo
      else if (num == 1 || num == 2) fibonacciFunctionHelper(num + 1, 1, 1)
      else fibonacciFunctionHelper(num + 1, accumulatorMinusOne, accumulatorMinusOne + accumulatorMinusTwo)
    }
    fibonacciFunctionHelper(1, 1, 1)
  }

  def isPrime(num: Int): Boolean = {
    def isPrimTailrec(delimiter: Int, isStillPrime: Boolean): Boolean = {
      if (!isStillPrime) false
      else if (delimiter < 2) isStillPrime
      else isPrimTailrec(delimiter - 1, num % delimiter != 0)
    }

    if (num % 2 == 0) false
    isPrimTailrec(num / 2, true)
  }

  def illegalArgumentHandler(num: Int): Unit = {
    if(num < 0) throw new IllegalArgumentException(s"#illegalArgumentHandler: illegal parameter $num")
  }

  def assertEquals(expected: Int, actual: Int): Unit = {
    if (expected != actual) throw new IllegalArgumentException(s"$expected not equals $actual")
  }

  def assertEquals(expected: String, actual: String): Unit = {
    if (!expected.equals(actual)) throw new IllegalArgumentException(s"$expected not equals $actual")
  }

  def assertTrue(expected: Boolean): Unit = {
    if (!expected) throw new IllegalArgumentException("Assertion Error. Suppose To be True")
  }

  def assertFalse(expected: Boolean): Unit = {
    if (expected) throw new IllegalArgumentException("Assertion Error. Suppose to be False")
  }

  def assertThrows(fx: Unit => Int): Unit = {
    try {
      fx.apply()
    } catch {
      case ex: Exception => println(s"#assertThrows: ${ex.getLocalizedMessage}")
        return
    }
    throw new IllegalArgumentException("Exception is not thrown.")
  }

  assertEquals("HelloHelloHello", concat("Hello", 3))

//  assertThrows(a => fibonacciFunction(-1))
  assertEquals(21, fibonacciFunction(8))

  assertTrue(isPrime(37))
  assertTrue(isPrime(2003))
  assertFalse(isPrime(6))

}
