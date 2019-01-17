package lectures

object DefaultArgs extends App {

  val num: Int = 5

  def factorial(acc: Int = 1, num: Int): Int =
    if (num < 2) acc
    else factorial(acc*num, num - 1)


  println(factorial(num = num))

}
