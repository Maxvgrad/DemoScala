package exercises

class PocketCalculator {

  def add(x: Int, y: Int): Int = addInternal(x, y)

  def subtract(x: Int, y: Int): Int = addInternal(x, y)

  def multiply(x: Int, y: Int): Int =
    try {
      Math.multiplyExact(x, y)
    } catch {
      case e: ArithmeticException => throw new OverFlowException
    }

  def divide(x: Int, y: Int): Double = {
    if (y == 0) throw new MathCalculationException
    else x / y
  }

  private def addInternal(x: Int, y: Int): Int = {
    if (y > 0) {
      val result = x + y
      if ((y ^ result) < 0)
        throw new OverFlowException
      return result
    } else {
      val result = x + y
      if ((y ^ result) < 0)
        throw new UnderFlowException
      return result
    }
  }
}

class OverFlowException extends RuntimeException

class UnderFlowException extends RuntimeException

class MathCalculationException extends RuntimeException


object Main extends App {

  val calculator: PocketCalculator = new PocketCalculator

  try {
    calculator.add(1, Int.MaxValue)
  } catch {
    case e: OverFlowException => println(e.getClass)
  }

  try {
    calculator.add(-1, Int.MinValue)
  } catch {
    case e: UnderFlowException => println(e.getClass)
  }

  println(s"2+2=${calculator.add(2, 2)}")

  try {
    calculator.multiply(2, Int.MaxValue)
  } catch {
    case e: OverFlowException => println(e.getClass)
  }

  println(s"2*2=${calculator.multiply(2, 2)}")

  try {
    calculator.divide(2, 0)
  } catch {
    case e: MathCalculationException => println(e.getClass)
  }

  println(s"4/2=${calculator.divide(4, 2)}")

}