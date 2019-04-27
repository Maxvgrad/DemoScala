package demo.commands
import demo.files.{DirEntry, File}
import demo.filesystem.State

class Touch(override val name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State, entryName: String): DirEntry = File.empty(state.wd.parentPath, entryName)
}
