package com.udemy.basics.demo.filesystem

import java.util.Scanner

import com.udemy.basics.demo.commands.Command
import com.udemy.basics.demo.files.Directory

object FileSystem extends App {

  val root = Directory.ROOT
  var state = State(root, root)
  val scanner = new Scanner(System.in)

  while (true) {
    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }

}
