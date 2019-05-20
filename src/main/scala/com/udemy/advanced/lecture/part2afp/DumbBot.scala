package com.udemy.advanced.lecture.part2afp

import scala.io.Source

object DumbBot extends App {

  Source.stdin.getLines().foreach((line: String) => println(pFuncion(line)))

  def pFuncion: PartialFunction[String,String] = {
    case "hello" => "Привет"
    case "bye" => "Пока"
    case _ => "Repeat, please"
  }

}
