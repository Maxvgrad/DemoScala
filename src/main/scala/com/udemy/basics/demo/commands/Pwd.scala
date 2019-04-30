package com.udemy.basics.demo.commands
import com.udemy.basics.demo.filesystem.State

class Pwd extends Command {

  override def apply(state: State): State =
    state.setMessage(state.wd.path)

}
