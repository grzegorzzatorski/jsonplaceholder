package com.gzatorski.jsonplaceholder.io

import com.gzatorski.jsonplaceholder.model.Post
import java.nio.file.FileAlreadyExistsException
import com.typesafe.scalalogging.LazyLogging
import spray.json.DefaultJsonProtocol._
import spray.json._

object PostWriter extends FileWriter with LazyLogging {

  def writePosts(posts: List[Post], directory: String, overwrite: Boolean): Unit = {
    implicit val postFormat: RootJsonFormat[Post] = jsonFormat4(Post)
    posts.foreach { post =>
      val json = post.toJson
      val jsonPretty = json.prettyPrint
      val writePath = s"$directory/${post.id}.json"

      try {
        writeFile(writePath, jsonPretty, overwrite)
        logger.trace(s"File $writePath has been saved")
      } catch {
        case _: FileAlreadyExistsException =>
          logger.error(s"File $writePath already exists. " +
            s"If you want to overwrite it, please set download.overwrite=true in application.conf")
      }
    }
  }

}
