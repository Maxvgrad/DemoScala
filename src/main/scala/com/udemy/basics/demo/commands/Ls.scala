package com.udemy.basics.demo.commands
import com.udemy.basics.demo.files.DirEntry
import com.udemy.basics.demo.filesystem.State

class Ls extends Command {

  override def apply(state: State): State = {
    val contents = createNiceOutput(state.wd.content)
    state.setMessage(contents)
  }

  def createNiceOutput(contents: List[DirEntry]): String =
    if(contents.isEmpty) ""
    else {
      val dirEntry = contents.head
      dirEntry.name + "[" + dirEntry.getType + "]\n" + createNiceOutput(contents.tail) + "\n"
    }
}
