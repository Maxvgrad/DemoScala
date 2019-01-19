package lectures.part1basics

object ValuesVariablesTypes extends App {

  val x: Int = 400
  println(x)

  val y: Float = 3
  println(y)

  val age = 26
  println("My age is: " + age)

  val isMale = true
  val isBland = false
  val lengthOfDick: Short = 27
  printf("Length of my dick: %d\n", lengthOfDick)

  //variables are mutable
  var variable: Int = 55
  variable = 65
  printf("This is a variable: %d\n", variable)
}
