package com.udemy.basics.exercises

import scala.annotation.tailrec
import scala.collection.immutable.Queue

class SocialNetwork(container: Map[Person, Set[Person]]) {

  /**
    * Friend two persons
    * @param person1 - person one
    * @param person2 - person two
    */
  def friend(person1: Person, person2: Person): SocialNetwork = {
    val friendSetOne = container(person1) + person2
    val friendSetTwo = container(person2) + person1
    SocialNetwork(container + (person1 -> friendSetOne) + (person2 -> friendSetTwo))
  }

  /**
    * Break Friendship of two persons
    * @param person1 - person one
    * @param person2 - person two
    */
  def unfriend(person1: Person, person2: Person): SocialNetwork = {
    val friendSetOne = container(person1) - person2
    val friendSetTwo = container(person2) - person1
    SocialNetwork(container + (person1 -> friendSetOne) + (person2 -> friendSetTwo))
  }

  /**
    * Add person to social network
    */
  def add(p: Person): SocialNetwork =
    if(container.contains(p)) this
    else SocialNetwork(container + (p -> Set()))

  /**
    * Remove person from social network
    */
  def remove(p: Person): SocialNetwork = {
    @tailrec
    def removeFriendHelper(friends: Set[Person], acc: SocialNetwork): SocialNetwork =
      if(friends.isEmpty) acc
      else removeFriendHelper(friends.tail, unfriend(p, friends.head))

    val friendsSet = container(p)
    val containerWithoutPerson = container - p
    removeFriendHelper(friendsSet, SocialNetwork(containerWithoutPerson))
  }

  /**
    * Get specified person friends set
    */
  def getFriends(person: Person) = container(person)

  /**
    * Check if specified person in SocialNetwork
    */
  def contains(person: Person) = container.contains(person)

  /**
    * Number of person without friends ;(
    */
  def numberOfPersonWithoutFriends() = container.values.count(_.isEmpty)

  /**
    * Top friendly person
    */
  def getTopFriendlyPerson() = container.maxBy(p => p._2.size)._1

  /**
    * Check if specified Persons has a connection
    */
  def socialConnection(person1: Person, person2: Person): Boolean = {
    @tailrec
    def breadthFirstSearch(queue: Queue[Person], history: Set[Person], target: Person): Boolean =
      if(queue.isEmpty) false
      else if (queue.head.equals(target)) true
      else {
        val friends = getFriends(queue.head)
        breadthFirstSearch(queue.tail ++ friends.diff(history), history ++ friends, target)
      }

    breadthFirstSearch(Queue(person1), Set(), person2)
  }
}

object SocialNetwork {
  def apply(container: Map[Person, Set[Person]]): SocialNetwork = new SocialNetwork(container.withDefaultValue(Set()))
}


case class Person(name: String) {}
