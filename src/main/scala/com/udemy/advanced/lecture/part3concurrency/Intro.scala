package com.udemy.advanced.lecture.part3concurrency

import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.{Executors, TimeUnit}

object Intro extends App {

  // JVM Threads
  val aThread = new Thread(() => println("thread"))

  aThread.start()
  aThread.join()

  println("main")

  // executors

  val pool = Executors.newFixedThreadPool(10)

  pool.execute(() => {

    TimeUnit.SECONDS.sleep(1)

  })
}

class BankAccount(@volatile var amount: Int) {
  override def toString: String = "" + amount
}

class SimpleContainer(capacity: Int) {

  private var value = 0

  private val lock = new ReentrantLock()

  def isEmpty: Boolean = value == 0

  def isFit(value: Int) = value + this.value < capacity

  def set(value: Int) = {
    lock.lock()
    this.value += value
    lock.unlock()
  }

  def get = {
    lock.lock()
    val result = 1
    value -= 1
    lock.unlock()
    result
  }
}
