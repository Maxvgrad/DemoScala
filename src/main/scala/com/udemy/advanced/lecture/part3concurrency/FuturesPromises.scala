package com.udemy.advanced.lecture.part3concurrency

import java.util.concurrent.TimeUnit

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success}

object FuturesPromises extends App {

  def calculateTheMeaningOfLife: Int = {
    TimeUnit.SECONDS.sleep(1)
    42
  }

  val aFuture = Future {
    calculateTheMeaningOfLife
  } // (global)


  println(aFuture.value)

  aFuture.onComplete(t => t match {
    case Success(value) => println(s"Success $value")
    case Failure(ex) => println(s"Success $ex")
  })

  TimeUnit.SECONDS.sleep(2)

  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile) = println(s"$name poke ${anotherProfile.name}")
  }

  object SocialNetwork {

    // db
    val names = Map(
      "1" -> "Mark",
      "2" -> "Bill",
      "3" -> "Dummy"
    )

    val friends = Map (
      "1" -> "2"
    )

    val random = new Random

    // api

    def fetchProfile(id: String): Future[Profile] = Future {
      Thread.sleep(random.nextInt(500))
      Profile(id, names(id))
    }

    def fetchBestFriend(id: String): Future[Profile] = Future {
      Thread.sleep(random.nextInt(500))
      val friendId = friends(id)
      Profile(friendId, names(friendId))
    }
  }

  val markFuture = SocialNetwork.fetchProfile("1")

  println(markFuture.value)

  markFuture.onComplete {
    case Success(markProfile) => {
      println("success1")
      val billFuture = SocialNetwork.fetchBestFriend(markProfile.id)
      billFuture.onComplete {
        case Success(billProfile) => {
          println("success2")
          markProfile.poke(billProfile)
        }
        case Failure(ex) => ex.printStackTrace()
      }
    }
    case Failure(ex) => ex.printStackTrace()
  }

  TimeUnit.SECONDS.sleep(1)

  // functional composition
  // map filter flatMap

  val nameOnTheWall = markFuture.map(profile => profile.name)

  val marksBestFriend = markFuture.flatMap(profile => SocialNetwork.fetchBestFriend(profile.id))

  val bestFriendRestricted = marksBestFriend.filter(profile => profile.name.startsWith("Z"))

  // for-comprehension

  for {
    mark <- SocialNetwork.fetchProfile("1")
    bill <- SocialNetwork.fetchBestFriend(mark.id)
  } yield mark.poke(bill)

  TimeUnit.SECONDS.sleep(3)

  // fallbacks

  val aProfileNoMatterWhat = SocialNetwork.fetchProfile("not in db").recover {
    case e: Throwable => Profile("3", "Dummy")
  }

  val aFutureNoMatterWhat = SocialNetwork.fetchProfile("not in db").recoverWith {
    case e: Throwable => SocialNetwork.fetchProfile("3")
  }

  val fallBackResult = SocialNetwork.fetchProfile("not in db").fallbackTo(SocialNetwork.fetchProfile("3"))


  // online banking app

  case class User(name: String)
  case class Transaction(sender: User, reciver: User, amound: Double, status: String)


  object BankingApp {

    def fetchUser(name: String) = Future {
      Thread.sleep(500)
      User(name)
    }

    def createTransaction(user: User, merchantName: String, amount: Double) = Future {
      Thread.sleep(1000)
      Transaction(user, User(merchantName), amount, "success")
    }

    def purchase(userName: String, merchantName: String, item: String, cost: Double): String = {

      val transactionStatus = for {
        user <- fetchUser(userName)
        transaction <- createTransaction(user, merchantName, cost)
      } yield transaction.status

      Await.result(transactionStatus, 2.seconds)
    }

  }


  println(BankingApp.purchase("Max", "Apple", "iphone", 900))

  // promises

  val promise = Promise[Int]() // controller over the future
  val future = promise.future

  // thread 1 - "consumer"

  future.onComplete {
    case Success(c) => println(s"[consumer] Success: $c")
  }

  // thread 2 - "producer"

  val producer = new Thread(() => {
    Thread.sleep(400)
    promise.success(42)
    println("[producer] done")
  }).start()

  Thread.sleep(1000)



}