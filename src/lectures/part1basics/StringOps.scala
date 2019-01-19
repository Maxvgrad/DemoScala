package lectures.part1basics

object StringOps extends App {


  val aNumberString = "42"
  val aNumberLong = aNumberString.toLong
  val aNumberDouble = aNumberString.toDouble
  val aNumberFloat = aNumberString.toFloat


  println('a' + aNumberString)
  println('a' +: aNumberString)
  println(aNumberString :+ 'b')

  println(aNumberString.reverse)
  println(aNumberString.take(1))

  // S-interpolators

  println(s"Float: $aNumberFloat")
  println(s"Float + Long: ${aNumberFloat + aNumberLong}")

  // F-interpolators

  println(f"Float: $aNumberFloat%2.3f")


  // R-intrpolator
  println(raw"\t\t\t\n\\Hello")
}
