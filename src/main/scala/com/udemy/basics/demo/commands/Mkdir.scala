package com.udemy.basics.demo.commands
import com.udemy.basics.demo.files.{DirEntry, Directory}
import com.udemy.basics.demo.filesystem.State

class Mkdir(override val name: String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State, entryName: String): DirEntry = Directory.empty(state.wd.path, entryName)
}
