package com.udemy.basics.demo.commands
import com.udemy.basics.demo.files.{Directory, File}
import com.udemy.basics.demo.filesystem.State

import scala.annotation.tailrec

class Echo(args: List[String]) extends Command {
  override def apply(state: State): State = {
    if (args.isEmpty) state
    else if (args.length == 1) state.setMessage(args.head)
    else {
      val operator = args(args.length - 2)
      val fileName = args.last
      val content = createContent(args, args.length - 2)

      if (">>".equals(operator))
        doEcho(fileName, content, true, state)
      else if (">".equals(operator))
        doEcho(fileName, content, true, state)
      else
        state.setMessage(createContent(args, args.length))

    }
  }

  def createContent(strings: List[String], contentSize: Int): String = {
    @tailrec
    def createContentHelper(accumulator: String, curIndex: Int): String =
      if (curIndex == contentSize) accumulator
      else createContentHelper(accumulator + strings(curIndex) + " ", curIndex+1)

    createContentHelper("", 0)
  }

  def getRootAfterEcho(currentDirectory: Directory, path: List[String], content: String, append: Boolean): Directory = {
    if (path.isEmpty) currentDirectory
    else if (path.tail.isEmpty) {
      val dirEntry = currentDirectory.findEntry(path.head)

      if (dirEntry == null)
        currentDirectory.addEntry(new File(currentDirectory.path, path.head, content))
      else if (dirEntry.isDirectory) currentDirectory
      else
        if (append) currentDirectory.replaceEntry(path.head, dirEntry.asFile.appendContents(content))
        else currentDirectory.replaceEntry(path.head, dirEntry.asFile.setContents(content))

    } else {
      val nextDirectory = currentDirectory.findEntry(path.head).asDirectory
      val newNextDirectory = getRootAfterEcho(nextDirectory, path.tail, content, append)

      if (newNextDirectory == nextDirectory) currentDirectory
      else currentDirectory.replaceEntry(path.head, newNextDirectory)
    }
  }

  def doEcho(fileName: String, content: String, append: Boolean, state: State) = {
    if (fileName.contains(Directory.SEPARATOR))
      state.setMessage("Echo: file name must not to contain separators")
    else {
      val newRoot: Directory = getRootAfterEcho(state.root, state.wd.getAllFoldersInPath :+ fileName, content, append)

      if (newRoot == state.root) {
        state.setMessage("")
      } else
        State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath))
    }
  }
}
