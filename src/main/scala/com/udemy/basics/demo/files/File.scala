package com.udemy.basics.demo.files

class File(override val parentPath: String, override val name: String, val content: String) extends DirEntry(parentPath, name) {

  override def asDirectory: Directory = throw new UnsupportedOperationException

  override def getType: String = "F"

  override def asFile: File = this

  override def isDirectory: Boolean = false

  override def isFile: Boolean = true

  def setContents(newContent: String) = new File(parentPath, name, newContent)

  def appendContents(newContent: String) = new File(parentPath, name, content + "\n" + newContent)

}

object File {
  def empty(parentPath: String, name: String): File = new File(parentPath, name, "")
}
