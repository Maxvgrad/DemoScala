package part2oop

object MethodNotation extends App {

  class Person(val name: String, favoriteMovie: String) {
    def isLikes(movie: String) = this.favoriteMovie.equalsIgnoreCase(movie)

    def hangOut(person: Person): String = s"${this.name} hang out with ${person.name}"

    def unary_! : String = this.name.reverse

    def isAlive: Boolean = true

    def apply(): String = s"Hi my name is $name an i like $favoriteMovie"
  }

  val elen = new Person("Elen", "Fight club")

  // infix notation = operator notation (syntactic sugar)
  println(elen isLikes "fight club")

  val max = new Person("Max", "Inception")

  println(elen hangOut max)

  println(s"Unary Elen: ${!elen}")

  //prefix notation

  val negativeY = -1
  val negativeX = 1.unary_-
  println(negativeY == negativeX)

  println(s"unary_! : ${true.unary_!}")

  //postfix notation
  println(elen isAlive)

  //apply
  println(max apply)
  println(max())
}
