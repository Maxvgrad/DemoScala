package demo.commands
import demo.files.{DirEntry, Directory}
import demo.filesystem.State

class Mkdir(override val name: String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State, entryName: String): DirEntry = Directory.empty(state.wd.path, entryName)
}
