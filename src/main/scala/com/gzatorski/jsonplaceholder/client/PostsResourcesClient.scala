package com.gzatorski.jsonplaceholder.client

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import com.gzatorski.jsonplaceholder.model.{JsonParser, Post}

import scala.concurrent.{ExecutionContext, Future}

class PostsResourcesClient(ac: ActorSystem) extends Client(ac) {

  def getAllPosts(requestURL: String)(implicit ec: ExecutionContext): Future[List[Post]] = {
    val httpRequest = HttpRequest(uri = requestURL)
    logger.info(s"Making a request: ${httpRequest.toString()}")
    performRequest(httpRequest).map(JsonParser.getAsPosts)
  }

}