package com.udemy.basics.lectures.part4pmatching

class Expressions {

  def show(e: Expr): String = {

    def showHelper(e: Expr): String = {
      e match {
        case Number(n) => s"$n"
        case Sum(e1, e2) => showHelper(e1) + " + " + showHelper(e2)
        case Prod(e1, e2) => {

          def pattern: PartialFunction[Expr, String] = {
            case Sum(_, _) => "(%s)"
            case _ => "%s"
          }
          pattern(e1).format(showHelper(e1)) + " * " + pattern(e2).format(showHelper(e2))
        }
      }
    }
    showHelper(e)
  }
}

trait Expr
case class Number(n: Int) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
case class Prod(e1: Expr, e2: Expr) extends Expr