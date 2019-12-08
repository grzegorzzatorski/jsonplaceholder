package com.gzatorski.jsonplaceholder.io

import java.nio.file.{Files, Paths, StandardOpenOption}


class FileWriter {

  def writeFile(filepath: String, content: String, overwrite: Boolean): Unit = {

    val path = Paths.get(filepath)
    val dirsExists = Files.exists(path.getParent)

    if (!dirsExists) {
      createDirs(filepath)
    }

    if (overwrite) {
      Files.write(path,
        content.getBytes,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING)
    } else {
      Files.write(path,
        content.getBytes,
        StandardOpenOption.CREATE_NEW,
        StandardOpenOption.TRUNCATE_EXISTING)
    }
  }

  private def createDirs(filepath: String) = {
    Files.createDirectories(Paths.get(filepath).getParent)
  }

}