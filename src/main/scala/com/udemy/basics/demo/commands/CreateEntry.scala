package com.udemy.basics.demo.commands
import com.udemy.basics.demo.files.{DirEntry, Directory}
import com.udemy.basics.demo.filesystem.State

abstract class CreateEntry(val name: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name))
      state.setMessage(s"Entry $name already exists!")
    else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(s"$name: must not containt path separators")
    } else if (checkIllegal(name)) {
      state.setMessage(s"$name: illegal entry name")
    } else {
      doCreateEntry(state, name)
    }
  }

  def checkIllegal(name: String) = name.contains(".")

  def doCreateEntry(state: State, str: String): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {

      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head)
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry.asDirectory, path.tail, newEntry))
      }
    }

    val wd = state.wd

    val allDirsInPath = wd.getAllFoldersInPath

    val newEntry = createSpecificEntry(state, str)

    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }

  def createSpecificEntry(state: State, entryName: String): DirEntry
}
