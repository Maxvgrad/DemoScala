package com.udemy.basics.exercises

import org.scalatest.{FlatSpec, FunSuite}

class SocialNetworkTest extends FlatSpec {

  "friend" should "succeed" in {
    //given
    val sNetwork = SocialNetwork(Map())
    val p1 = Person("X")
    val p2 = Person("Y")
    //when
    val sNetworkWithFriends = sNetwork.friend(p1, p2)
    //then
    assertResult(true) {
      sNetworkWithFriends.getFriends(p1).contains(p2)
    }
    assertResult(true) {
      sNetworkWithFriends.getFriends(p2).contains(p1)
    }
  }

  "unfriend" should "succeed" in {
    //given
    val sNetwork = SocialNetwork(Map())
    val p1 = Person("X")
    val p2 = Person("Y")
    //when
    val sNetworkWithFriends = sNetwork.friend(p1, p2)
    val sNetworkWithoutFriends = sNetworkWithFriends.unfriend(p1, p2)
    //then
    assertResult(false) {
      sNetworkWithoutFriends.getFriends(p1).contains(p2)
    }
    assertResult(false) {
      sNetworkWithoutFriends.getFriends(p2).contains(p1)
    }
  }

  "add" should "succeed" in {
    //given
    val sNetwork = SocialNetwork(Map())
    val p1 = Person("X")
    //when
    val sNetworkWithPerson = sNetwork.add(p1)
    //then
    assertResult(true) {
      sNetworkWithPerson.contains(p1)
    }
  }

  "remove" should "succeed" in {
    //given
    val sNetwork = SocialNetwork(Map())
    val p1 = Person("X")
    //when
    val sNetworkWithPerson = sNetwork.add(p1)
    //then
    assertResult(true) {
      sNetworkWithPerson.contains(p1)
    }
  }

  "number of person without friends" should "be 0" in {
    //given
    val sNetwork = SocialNetwork(Map())
    //when - then
    assertResult(0) {
      sNetwork.numberOfPersonWithoutFriends()
    }
  }

  "number of person without friends" should "be 3" in {
    //given
    val sNetwork1 = SocialNetwork(Map())
    val sNetwork2 = sNetwork1.add(Person("Jon"))
    val sNetwork3 = sNetwork2.add(Person("Jon2"))
    val sNetwork4 = sNetwork3.add(Person("Jon4"))
    //when - then
    assertResult(3) {
      sNetwork4.numberOfPersonWithoutFriends()
    }
  }

  "top friendly person" should "success" in {
    //given
    val sNetwork1 = SocialNetwork(Map())
    val topPerson = Person("top")
    val p1 = Person("p1")
    val p2 = Person("p2")
    val p3 = Person("p3")
    val sNetwork2 = sNetwork1.friend(p1, topPerson)
    val sNetwork3 = sNetwork2.friend(p2, topPerson)
    val sNetwork4 = sNetwork3.friend(p3, topPerson)
    val sNetwork5 = sNetwork4.friend(p1, p2)
    //when - then
    assertResult(topPerson) {
      sNetwork5.getTopFriendlyPerson()
    }
  }

  "social connection" should "indirect connection success" in {
    //given
    val sNetwork1 = SocialNetwork(Map())
    val p0 = Person("p0")
    val p1 = Person("p1")
    val p2 = Person("p2")
    val p3 = Person("p3")
    val p4 = Person("p4")
    val sNetwork2 = sNetwork1.friend(p1, p0)
    val sNetwork3 = sNetwork2.friend(p1, p2)
    val sNetwork4 = sNetwork3.friend(p2, p3)
    val sNetwork5 = sNetwork4.friend(p3, p4)
    //when - then
    assertResult(true) {
      sNetwork5.socialConnection(p0, p4)
    }
  }



  "social connection" should "failed" in {
    //given
    val sNetwork1 = SocialNetwork(Map())
    val p0 = Person("p0")
    val p1 = Person("p1")
    val p2 = Person("p2")
    val p3 = Person("p3")
    val p4 = Person("p4")
    val p5 = Person("p5")
    val sNetwork2 = sNetwork1.friend(p1, p0)
    val sNetwork3 = sNetwork2.friend(p2, p3)
    val sNetwork4 = sNetwork3.friend(p3, p4)
    val sNetwork5 = sNetwork3.add(p5)
    //when - then
    assertResult(false) {
      sNetwork4.socialConnection(p0, p5)
    }
  }



}
