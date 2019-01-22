package part2oop

object OOBasics extends App {

  val person = new Person("Max", 26)

  println(person.age)
  println(person.x)

  person.ageUp()
  println(person.age)

  person.greet("Jon")
  person.greet

  var person2  = new Person
  person2.greet


  println("---")

  val bukowski = new Writer("Charles", "Bukowski", 1920)

  val novel = new Novel("The Pulp", 1989, bukowski)

  println(bukowski.getYear())
  println(bukowski.fullName())

  println(novel.author)
  println(novel.isWrittenByAuthor(new Writer("Max", "Vinogradov", 1992)))
  println(novel.copy(1991))

  val counter = new Counter(3)

  println(counter.num)
  println(counter.increment(4).num)
}

//constructor
class Person(name: String, var age: Int) {
  val x = 2;

  def this(name: String) = this(name, 0)

  def this() = this("default")

  def ageUp(): Unit = {
    age = age + 1
  }

  def greet(name: String): Unit = println(s"${this.name} says: Hello, $name")

  //overloading
  def greet(): Unit = println(s"Hello, my name is ${name}")
}

// class parameters are not fields

class Writer(firstName: String, surname: String, year: Int) {
  def fullName(): String =
    s"$firstName $surname"

  def getYear(): Int =
    year
}

class Novel(name: String, releaseYear: Int, val author: Writer) {

  def isWrittenByAuthor(author: Writer) = this.author.equals(author)

  def copy(newYearOfRelease: Int) = new Novel(name, newYearOfRelease, author)
}

class Counter(val num: Int) {

  def increment(num: Int) = new Counter(this.num + num)

  def increment = increment(1)

  def decrement(num: Int) = new Counter(this.num - num)

  def decrement = decrement(1)
}