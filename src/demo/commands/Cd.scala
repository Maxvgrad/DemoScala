package demo.commands
import demo.files.{DirEntry, Directory}
import demo.filesystem.State

import scala.annotation.tailrec

class Cd(val dir: String) extends Command {
  override def apply(state: State): State = {

    //find roo
    val root = state.root
    val wd = state.wd

    val absolutePath =
      if (dir.startsWith(Directory.SEPARATOR)) dir
      else if (wd.isRoot) wd.path + dir
      else wd.path + Directory.SEPARATOR + dir

    val destinationDirectory = doFindEntry(root, absolutePath)

    if (destinationDirectory == null || !destinationDirectory.isDirectory)
      state.setMessage(dir + ": no such directory")
    else
      State(root, destinationDirectory.asDirectory)
  }

  def doFindEntry(directory: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(curDir: Directory, path: List[String]): DirEntry = {
      if (path.isEmpty || path.head.isEmpty) curDir
      else if (path.tail.isEmpty) curDir.findEntry(path.head)
      else {
        val nextDir = curDir.findEntry(path.head)
        if (nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }

    @tailrec
    def eliminateDotsHelper(accumulator: List[String], rawPath: List[String]): List[String] = {
      if (rawPath.isEmpty) accumulator
      else {
        val head = rawPath.head
        if (".".equals(head)) eliminateDotsHelper(accumulator, rawPath.tail)
        else if ("..".equals(head)) eliminateDotsHelper(accumulator.dropRight(1), rawPath.tail)
        else eliminateDotsHelper(accumulator :+ head, rawPath.tail)
      }
    }

    val tokens = path.substring(1).split(Directory.SEPARATOR).toList

    findEntryHelper(directory, eliminateDotsHelper(List(), tokens))
  }

}
