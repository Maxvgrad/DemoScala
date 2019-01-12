package lectures

object Functions extends App {

  def aFunction(str: String, num: Int): String =
    str+num

  println(aFunction("How much: ", 3))

  def functionWithoutParameters(): Int = 33
  println(functionWithoutParameters())
  println(functionWithoutParameters)

  def concatFunc(strForRepeat: String, times: Int): String = {
    if(times == 1) strForRepeat.substring(0, strForRepeat.length-1)
    else strForRepeat + concatFunc(strForRepeat, times-1)
  }

  println(concatFunc("Hello-", 3))

  //when you need loops please use recursion
  val name = "Max"
  val age = 26

  def sideEffectFunction(str: String): Unit = {
    println(str*2)
  }
  sideEffectFunction("SideEffect")

  def greetingFunction(name: String): Unit = printf("Hello, my name is %s, is %d years old", name, age)

  greetingFunction("Jon");

  def factorialFunction(ofNumber: Int): Int = {
    illegalArgumentHandler(ofNumber)
    if(ofNumber == 0) 1
    else ofNumber * factorialFunction(ofNumber-1)
  }

  def fibonacciFunction(ofNumber: Int): Int = {
    illegalArgumentHandler(ofNumber)
    if (ofNumber == 1) 1
    else if (ofNumber == 2) 1
    else fibonacciFunction(ofNumber-2) + fibonacciFunction(ofNumber-1)
  }

  def isPrime(number: Int): Boolean = {
    //todo
    true
  }

  def illegalArgumentHandler(num: Int): Unit = {
    if(num < 0) throw new IllegalArgumentException(s"#factorialFunction: illegal parameter $num")
  }

  def assertEquals(expected: Int, actual: Int): Unit = {
    if (expected != actual) throw new IllegalArgumentException(s"$expected not equals $actual")
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

  println("assertions framework")
  assertThrows(a => factorialFunction(-1))
  assertEquals(1, factorialFunction(0))
  assertEquals(1, factorialFunction(1))
  assertEquals(6, factorialFunction(3))

  assertThrows(a => fibonacciFunction(-1))
  assertEquals(21, fibonacciFunction(8))

  assertTrue(isPrime(37))
  assertTrue(isPrime(2003))
  assertFalse(isPrime(6))
}
