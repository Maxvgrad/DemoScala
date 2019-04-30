package lectures.part3fp

object AnonymousFunctions extends App {


  val doubler = (x: Int) => x * 2 //anonymous function (LAMBDA)

  //multiple params in lambda
  val adder = (x: Int, y: Int) => y + x

  val supplier = () => 3

  println(supplier)
  println(supplier())

  val stringToInt = { (str: String) =>
    str.toInt
  }

  println(stringToInt("43"))

  //MOAR syntactic sugar

  val niceIncrementer: Int => Int = _ + 6
  println(niceIncrementer(4))

  val niceAdder: (Int, Int) => Int = _ + _
  println(niceAdder(4, 6))

}
