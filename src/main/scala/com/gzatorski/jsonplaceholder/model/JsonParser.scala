package com.gzatorski.jsonplaceholder.model

import spray.json._
import DefaultJsonProtocol._

object JsonParser {

  def getAsPosts(content: String): List[Post] = {
    implicit val postFormat: JsonFormat[Post] = jsonFormat4(Post)

    if (content.isEmpty) {
      List[Post]()
    } else {
      val parsedJson = content.parseJson
      parsedJson.convertTo[List[Post]]
    }
  }

}
