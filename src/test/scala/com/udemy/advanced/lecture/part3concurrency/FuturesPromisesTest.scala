package com.udemy.advanced.lecture.part3concurrency

import java.util.concurrent.TimeUnit

import org.scalatest.FlatSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Random, Success}

class FuturesPromisesTest extends FlatSpec {

  behavior of "FuturesPromises"

  it should "fulfill a future IMMEDIATELY with a value" in {

    val f = fulfill(4)

    println(f.value)

  }

  it should "inSequence run the futures" in {
    inSequence(Future{"Hello"}, Future{" Max"})
  }

  it should "inSeq" in {
    inSeq(Future{
      Thread.sleep(3000)
      "Hello"
    }, Future{" Max"}).onComplete(t => t match {
      case Success(s) => println(s)
    })

    Thread.sleep(4000)
  }

  it should "first completed" in {

    val oneSecond = 1
    val twoSecond = 2

    Future.firstCompletedOf(List(
      Future[Int] {
        TimeUnit.SECONDS.sleep(oneSecond)
        println(s"Time: $oneSecond")
        oneSecond
      }, Future[Int] {
        TimeUnit.SECONDS.sleep(twoSecond)
        println(s"Time: $twoSecond")
        twoSecond
      })).onComplete {

      case Success(value) => assertResult(oneSecond)(value)

    }
    TimeUnit.SECONDS.sleep(4)
  }

  it should "first completed custom impl" in {

    val oneSecond = 1
    val twoSecond = 2

    theFirst(
      Future[Int] {
        TimeUnit.SECONDS.sleep(oneSecond)
        println(s"Time: $oneSecond")
        oneSecond
      }, Future[Int] {
        TimeUnit.SECONDS.sleep(twoSecond)
        println(s"Time: $twoSecond")
        twoSecond
      }).onComplete {

      case Success(value) => assertResult(oneSecond)(value)

    }
    TimeUnit.SECONDS.sleep(4)
  }


  it should "the last" in {

    val oneSecond = 1
    val twoSecond = 2

    theLast(
      Future[Int] {
        TimeUnit.SECONDS.sleep(oneSecond)
        println(s"Time: $oneSecond")
        oneSecond
      }, Future[Int] {
        TimeUnit.SECONDS.sleep(twoSecond)
        println(s"Time: $twoSecond")
        twoSecond
      }).onComplete {

      case Success(value) => assertResult(twoSecond)(value)

    }

    TimeUnit.SECONDS.sleep(4)
  }


  it should "try complete with" in {

//    retryUntil(() => Future[Int]{
//
//      TimeUnit.SECONDS.sleep(2)
//
//
//      println("4")
//
//      4
//    }, (i: Int) => i % 2 == 0).onComplete({
//      case Success(value) => println(s"Val: $value")
//    })


    TimeUnit.SECONDS.sleep(4)
  }


  it should "retry until" in {

    retryUntil(() =>
      Future[Int] {
        Random.nextInt(20)
      }, (i: Int) => i == 3)
      .onComplete {

      case Success(value) => print("Success " + value)
      case Failure(ex) => print("Failure " + ex)

    }

    TimeUnit.SECONDS.sleep(3)
  }



  /** Return future with computed value
    */
  def fulfill[A](value: A) = {

    TimeUnit.SECONDS.sleep(4)

    Future(value)
  }

  def inSequence[A, B](fa: Future[A], fb: Future[B]) = {

    fa.onComplete {
      case Success(v) => {
        println(v)
        fb.onComplete{
          case Success(v2) => println(v2)
        }
      }
    }
  }

  def inSeq[A, B](fa: Future[A], fb: Future[B]) = {
    fa flatMap(_ => fb)
  }

  def theFirst[A](fa: Future[A], fb: Future[A]) = {

    val promise = Promise[A]()

    fa.onComplete {
      case Success(value) => if (!promise.isCompleted) promise.success(value)
      case Failure(th) => if (!promise.isCompleted) promise.failure(th)
    }

    fb.onComplete {
      case Success(value) => if (!promise.isCompleted) promise.success(value)
      case Failure(th) => if (!promise.isCompleted) promise.failure(th)
    }

    promise.future
  }

  def theLast[A](fa: Future[A], fb: Future[A]) = {

    val promise = Promise[A]()
    val lastPromise = Promise[A]()

    def checkOnComplete(pOne: Promise[A], pTwo: Promise[A], f: Future[A]) = {
      f.onComplete(t => {
        if (!pOne.tryComplete(t)) pTwo.tryComplete(t)
      })
    }

    checkOnComplete(promise, lastPromise, fa)
    checkOnComplete(promise, lastPromise, fb)

    lastPromise.future
  }


  def retryUntil[T](action: () => Future[T], condition: T => Boolean) = {

    val promise = Promise[T]()

    //@tailrec
    def retryUntilHelper(count: Int, action: () => Future[T], condition: T => Boolean): Future[T] = {
      println(s"count: $count")
      action() filter(e => {
        println(s"random val: $e")
        condition(e)
      }) recoverWith {
        case _ if count > 0 => retryUntilHelper(count - 1, action, condition)
      }
    }

    retryUntilHelper(5, action, condition) onComplete {
      case Success(t) => promise.success(t)
      case Failure(ex) => promise.failure(ex)
    }

    promise future
  }


}
