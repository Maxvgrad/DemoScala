package demo.files

abstract class DirEntry(val parentPath: String, val name: String) {

  def path: String = {
    val separator = if (Directory.ROOT_PATH.equals(parentPath)) ""
    else Directory.SEPARATOR

    parentPath + separator + name
  }

  def asDirectory: Directory
  def isDirectory: Boolean

  def asFile: File
  def isFile: Boolean

  def getType: String
}
