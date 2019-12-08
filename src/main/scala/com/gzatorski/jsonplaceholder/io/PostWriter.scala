package com.gzatorski.jsonplaceholder.io

import com.gzatorski.jsonplaceholder.model.Post
import java.nio.file.FileAlreadyExistsException
import spray.json.DefaultJsonProtocol._
import spray.json._

object PostWriter extends FileWriter {


  def writePosts(posts: List[Post], directory: String, overwrite: Boolean): Unit = {
    implicit val postFormat = jsonFormat4(Post)
    posts.foreach { post =>
      val json = post.toJson
      val jsonPretty = json.prettyPrint
      val writePath = s"$directory/${post.id}.json"

      try {
        writeFile(writePath, jsonPretty, overwrite)
      } catch {
        case e: FileAlreadyExistsException => println(s"File $writePath already exists. " +
          s"If you want to overwrite it, please set download.overwrite=true in application.conf")
      }
    }
  }

}
