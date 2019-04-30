package com.udemy.basics.demo.commands

import com.udemy.basics.demo.files.Directory
import com.udemy.basics.demo.filesystem.State

class Rm(name: String) extends Command {
  override def apply(state: State): State = {

    val wd = state.wd

    val absolutePath = if (name.startsWith(Directory.SEPARATOR)) name
    else if (wd.isRoot) wd.path + name
    else wd.path + Directory.SEPARATOR + name

    if (Directory.ROOT_PATH.equals(absolutePath)) throw new IllegalArgumentException
    else doRm(state, absolutePath)

  }

  def doRm(state: State, absolutePath: String) = {

    def rmHelper(directory: Directory, path: List[String]): Directory = {
      if (path.isEmpty) directory
      else if (path.tail.isEmpty) directory.removeEntry(path.head)
      else {
        val nextDirectory = directory.findDescendant(path.head)
        if (!nextDirectory.isDirectory) directory
        else {
          val newNextDirectory = rmHelper(nextDirectory.asDirectory, path.tail)
          if (nextDirectory == newNextDirectory) directory
          else directory.replaceEntry(path.head, newNextDirectory)
        }
      }
    }

    val tokens = absolutePath.substring(1).split(Directory.SEPARATOR).toList

    val newRoot: Directory = rmHelper(state.root, tokens)

    if (newRoot == state.root) state.setMessage(absolutePath + ": no such file or directory")
    else {
      State(newRoot, newRoot.findDescendant(state.wd.path.substring(1)))
    }

  }

}
