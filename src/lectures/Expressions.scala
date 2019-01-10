package lectures

object Expressions extends App {

  var x = 1 + 2
  println(x)

  println(2 + 3 * 3)

  // & | ^ >> << >>>

  println(3>>2)
  println(3<<2)

  println(2 == 2)
  println(!true)

  var aVar = 3
  aVar*=6 // += -= /= also works with side effects
  println(aVar)

  //Instructions (DO) vs expressions (VALUE)

  val aConditionVal = if (x < 5) 0 else 1
  println(aConditionVal)

  println("----unitVal")
  val unitVal = while (x < 10) { x += 1}
  println(unitVal)

  println("----aCodeBlock")
  val aCodeBlock: Boolean = {
    2 < 3
  }
  println("----helloWorld")
  val helloWorld = println("Hello world")
  println(helloWorld)

  val fromCodBlock = {
    if (aCodeBlock) 392 else 492
    5
  }

  println(fromCodBlock)

}
