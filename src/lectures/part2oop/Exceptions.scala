package lectures.part2oop

object Exceptions extends App {

  def getInt(flag: Boolean): Int =
    if (flag) throw new IllegalArgumentException
    else 44


  val potentiallyFail = try {
    getInt(true)
  } catch {
    case e: IllegalArgumentException => 42
    case e: RuntimeException => 43
  } finally {
    // user for side effects
    println("finally")
  }

  println(potentiallyFail)

  // out of memory error
  //val arr: Array[Int] = new Array(Int.MaxValue)
  //val arr = Array.ofDim(Int.MaxValue)

}
