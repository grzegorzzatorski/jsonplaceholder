package com.gzatorski.jsonplaceholder


import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.gzatorski.jsonplaceholder.client.PostsResourcesClient
import com.gzatorski.jsonplaceholder.io.PostWriter
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Await
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


object Launcher extends LazyLogging {

  def main(args: Array[String]): Unit = {
    logger.info("Starting up")

    val properties = Properties("properties.conf")
    implicit val ac: ActorSystem = ActorSystem("json-placeholder")

    val client = new PostsResourcesClient(ac)

    client.getAllPosts(properties.apiRequestAddress).andThen {
      case Success(value) =>
        logger.info(s"Received ${value.size} posts. " +
          s"Trying to persist into directory ${properties.downloadDirectory}. " +
          s"Overwrite existing files: ${properties.filesOverwite}")
        PostWriter.writePosts(
          value,
          properties.downloadDirectory,
          properties.filesOverwite)
      case Failure(f) => logger.error(s"Something goes wrong during processing http request: ${f.getMessage}")
    }.andThen {
      case _ =>
        Http().shutdownAllConnectionPools()
        ac.terminate()
        logger.info("Waiting for Actor System terminating")
    }

    Await.ready(ac.whenTerminated, Duration(1, TimeUnit.MINUTES))

  }

}