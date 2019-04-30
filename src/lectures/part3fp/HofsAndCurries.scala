package lectures.part3fp

object HofsAndCurries extends App {

  //HOF

  def nTymes(f: Int => Int, n: Int, x: Int): Int =
    if (n <= 0) x
    else nTymes(f, n-1, f(x))

  println(nTymes(x => x * x, 3, 2))
  println(nTymes(x => x + 1, 10, 1))

  def nTymesBetter(f: Int => Int, n: Int): (Int => Int) =
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTymesBetter(f, n - 1)(f(x))


  println(nTymesBetter(_ + 1, 10)(1))

  //curried function

  val superAdder: Int => Int => Int = (x: Int) => (x: Int) => x + x

  println(superAdder(1)(9))

  //function with multiple parameter lists
  def curriedFormatter(c: String)(x: Double) = c.format(x)

  val standardFormat = curriedFormatter("%4.2f")_

  println(standardFormat(Math.PI))

  // compose

  def compose[A, B, T](f: B => T, g: A => B):A => T = {
    x: A => f(g(x))
  }

  val tryCompose: Int => Int = compose((x: Int) => x * 2, (x: Int) => x + 2)

  val result: Int = tryCompose(4)
  println(s"compose(4) = $result")

  // andThen

  def andThen[A, B, T](f: A => B, g: B => T):A => T = {
    x: A => g(f(x))
  }

  val tryAndThen: Int => Int = andThen((x: Int) => x * 2, (y: Int) => y + 2)
  val result2: Int = tryAndThen(4)
  println(s"andThen(4) = $result2")


  // To curry test
  def toCurry[A, B, T](fun: (A, B) => T): A => B => T = {
    x:A => y: B => fun(x, y)
  }

  val curry = toCurry((x: Int, y: Int) => x + y)
  println(curry(2)(8))

  //to curry short way
  val curryShort = toCurry[Int, Int, Int](_ + _)


  // From curry
  def fromCurry[A, B, T](curry: A => B => T): (A, B) => T =
    (x: A, y: B) => curry(x)(y)

  println(fromCurry(curry)(7, 4))

}
