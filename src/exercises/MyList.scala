package exercises

abstract class MyList[+A]  {

  def head: A

  def tail: MyList[A]

  def add[B >: A](element: B): MyList[B]

  def isEmpty(): Boolean

  def filter(predicate: MyPredicate[A]): MyList[A]

  def map[E](transformer: MyTransformer[A, E]): MyList[E]

  def flatMap[E](transformer: MyTransformer[A, MyList[E]]): MyList[E]

  def printElements(): String

  def ++[B >: A](list: MyList[B]): MyList[B]

  override def toString: String = "[" + printElements + "]"
}

case object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyList[Nothing] = throw new NoSuchElementException

  override def add[B >: Nothing](element: B): MyList[B] = new Cons[B](element, Empty)

  override def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty

  override def map[E](transformer: MyTransformer[Nothing, E]): MyList[E] = Empty

  override def flatMap[E](transformer: MyTransformer[Nothing, MyList[E]]): MyList[E] = Empty

  override def isEmpty(): Boolean = true

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  override def printElements(): String = ""
}

case class Cons[+A](head: A, tail: MyList[A]) extends MyList[A] {

  override def add[B >: A](element: B): MyList[B] = new Cons[B](element, this)

  override def filter(predicate: MyPredicate[A]): MyList[A] =
    if (predicate.test(head))
      new Cons[A](head, tail.filter(predicate))
    else
      tail.filter(predicate)

  override def map[E](transformer: MyTransformer[A, E]): MyList[E] =
    new Cons[E](transformer.transform(head), tail.map(transformer))

  override def flatMap[E](transformer: MyTransformer[A, MyList[E]]): MyList[E] = {
    transformer.transform(head) ++ tail.flatMap(transformer)
  }

  override def isEmpty(): Boolean = false

  override def ++[B >: A](list: MyList[B]): MyList[B] = new Cons[B](head, tail ++ list)

  override def printElements(): String = head.toString + ", " + tail.printElements
}


trait MyPredicate[-A] {
  def test(element: A): Boolean
}

class EvenPredicate extends MyPredicate [Int] {
  override def test(element: Int): Boolean = element % 2 == 0
}

trait MyTransformer[-A, B] {
  def transform(input: A): B
}

class StringToIntTransformer extends MyTransformer[String, Int] {
  override def transform(input: String): Int = input length
}

class IntToMyList extends MyTransformer[Int, MyList[Int]] {
  override def transform(input: Int): MyList[Int] = new Cons[Int](input, new Cons[Int](input+1, Empty))
}


object ListTest extends App {
  val list = new Cons(1, new Cons(5, new Cons(2, Empty)))
  println(list.tail.head)

  println(list.toString)

  class Dog {
    override def toString: String = "Dog"
  }

  val genericList: Cons[Dog] = new Cons(new Dog, new Cons(new Dog, new Cons[Dog](new Dog, Empty)))
  println(genericList.toString)

  // filter test

  val listOfInts: Cons[Int] = new Cons(4, new Cons[Int](2, new Cons[Int](8, new Cons[Int](9, new Cons[Int](16, Empty)))))

  val listOfEvenInts = listOfInts.filter(new EvenPredicate)

  println(listOfEvenInts.toString)

  val listOfString: Cons[String] = new Cons[String]("Hello", new Cons[String]("Max", new Cons[String]("!", Empty)))

  val listOfStringsLength = listOfString map(new StringToIntTransformer)

  println(listOfStringsLength toString)

  println(listOfInts flatMap(new IntToMyList))

}
