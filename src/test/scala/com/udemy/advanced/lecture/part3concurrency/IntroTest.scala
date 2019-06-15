package com.udemy.advanced.lecture.part3concurrency

import java.util.concurrent.TimeUnit

import org.scalatest.FlatSpec

class IntroTest extends FlatSpec {

  behavior of "IntroTest"

  it should "race condition" in {

    def runInParallel = {

      var x = 0

      val thread1 = new Thread(() => x = 1)
      val thread2 = new Thread(() => x = 2)

      thread1.start()
      thread2.start()
      println(x)
    }

    for (_ <- 1 to 100) runInParallel
  }

  it should "synchronized" in {

    def buy(account: BankAccount, thing: String, price: Int): Unit = {
      account.synchronized {
        account.amount -= price
      }
    }

    for (_ <- 1 to 500) {

      val account = new BankAccount(50000)

      val aThread1 = new Thread(() => buy(account, "shoue", 3000)).start()
      val aThread2 = new Thread(() => buy(account, "iphone12", 4000)).start()

      TimeUnit.MILLISECONDS.sleep(100)

      if (account.amount != 43000) println("gotcha " + account.amount)

    }
  }

  it should "solve the task for inception threads" in {

    def greeting(n: Int): Unit = {
      if (n >= 50) println(s"Hello from thread #${n}")
      else {
        val t = new Thread(() => greeting(n + 1))
        t.start()
        t.join()
        println(s"Hello from thread #${n}")
      }
    }

    greeting(1)
  }

  it should "closures" in {

    var message = ""

    val awesomeThread = new Thread(() => {
      TimeUnit.SECONDS.sleep(1)
      message = "Scala awesome"
    })

    message = "Scala sucks"
    awesomeThread.start()

    TimeUnit.SECONDS.sleep(2)

    assertResult("Scala awesome")(message)
  }

  it should "producer and consumer" in {

    val container = new SimpleContainer(100)

    val consumer = new Thread(() => {
      println("#consumer: waiting...")
      while (container.isEmpty) {
        println("#consumer: actively waiting")
      }
      println("#consumer: consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("#producer: computing")
      TimeUnit.SECONDS.sleep(1)
      val value = 42
      println("#producer: value " + value)
      container.set(value)
    })

    consumer.start()
    producer.start()

    consumer.join()
    producer.join()
  }

  it should "producer and consumer wait/notify" in {

    val container = new SimpleContainer(100)

    val consumer = new Thread(() => {
      println("#consumer: waiting...")

      container.synchronized {

        while (container.isEmpty) {
          container.wait()
        }

        println("#consumer: consumed " + container.get)
      }
    })

    val producer = new Thread(() => {
      println("#producer: computing")
      TimeUnit.SECONDS.sleep(1)
      container.synchronized {
        val value = 42

        println("#producer: value " + value)
        container.set(value)
        container.notifyAll()
      }
    })

    consumer.start()
    producer.start()

    consumer.join()
    producer.join()
  }

  it should "multiple producers and consumers" in {

    val container = new SimpleContainer(2)

    var run = true

    val consumer: Runnable = () => {
      while (run) {
        container.synchronized {
          while (container.isEmpty) {
            println("#consumer: waiting...")
            container.wait()
          }
          println("#consumer: consumed " + container.get)
          container.notifyAll()
        }
      }
    }

    val producer: Runnable = () => {
      while (run) {
        container.synchronized {
          val value = 1
          if (container.isFit(value)) {
            println("#producer: value " + value)
            container.set(value)
            container.notifyAll()
          }
          println("#producer: waiting...")
          container.wait()
        }
      }
    }

    (1 to 10).foreach(i => {
      new Thread(producer).start()
      new Thread(consumer).start()
    })

    TimeUnit.SECONDS.sleep(3)

    run = false
  }

  it should "notify and notifyAll" in {

    val mutex = new Object

    (1 to 10).foreach(i => {

      new Thread(() => {
        mutex.synchronized {
          println(s"before wait $i")
          mutex.wait()
          println(s"after wait $i")
        }
      }).start()
    })

    TimeUnit.SECONDS.sleep(3)

    mutex.synchronized {
      mutex.notifyAll()
    }

  }

}
