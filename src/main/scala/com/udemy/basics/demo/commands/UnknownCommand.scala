package com.udemy.basics.demo.commands

import com.udemy.basics.demo.filesystem.State

class UnknownCommand extends Command {

  override def apply(state: State):State = state.setMessage("Command not found")

}
