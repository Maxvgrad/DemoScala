package com.udemy.basics.lectures.part2oop

object MethodNotation extends App {

  class Person(val name: String, val favoriteMovie: String, val age: Int = 0) {

    def isLikes(movie: String) = this.favoriteMovie.equalsIgnoreCase(movie)

    def hangOut(person: Person): String = s"${this.name} hang out with ${person.name}"

    def unary_! : String = this.name.reverse

    def isAlive: Boolean = true

    def apply(): String = s"Hi my name is $name an i like $favoriteMovie"

    def apply(n: Int) = s"${this.name} watched ${this.favoriteMovie} $n times"

    def +(favoriteMovie: String): Person =
      new Person(s"${this.name} ($favoriteMovie)", favoriteMovie);

    def unary_+ : Person = new Person(this.name, this.favoriteMovie, this.age + 1)

    def learnsScala = this learns "Scala"

    def learns(subject: String) = s"${this.name} learns $subject"

    override def toString() = s"Person(name: ${this.name}, favoriteMovie: $favoriteMovie, age: $age)"
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

  println(elen + "the rock star")

  val ksenia = new Person("Ksenia", "The Pulp Fiction", 25);

  println(+ksenia)

  println(ksenia learns "Math")

  println(ksenia learnsScala)

  println(ksenia(4))
}
