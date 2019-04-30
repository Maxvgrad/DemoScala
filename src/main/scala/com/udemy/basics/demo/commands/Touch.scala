package com.udemy.basics.demo.commands
import com.udemy.basics.demo.files.{DirEntry, File}
import com.udemy.basics.demo.filesystem.State

class Touch(override val name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State, entryName: String): DirEntry = File.empty(state.wd.parentPath, entryName)
}
