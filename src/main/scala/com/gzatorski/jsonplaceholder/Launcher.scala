package com.gzatorski.jsonplaceholder


import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.gzatorski.jsonplaceholder.io.PostWriter

import scala.concurrent.Await
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


object Launcher {

  def main(args: Array[String]): Unit = {

    val properties = Properties("properties.conf")
    implicit val ac = ActorSystem("json-placeholder")

    val client = new JSONPlaceholderClient(ac)

    client.getAllPosts(properties.apiRequestAddress).andThen {
      case Success(value) =>
        PostWriter.writePosts(
          value,
          properties.downloadDirectory,
          properties.filesOverwite)
      case Failure(f) => println(f)
    }.andThen {
      case _ =>
        Http().shutdownAllConnectionPools()
        ac.terminate()
    }

    Await.ready(ac.whenTerminated, Duration(1, TimeUnit.MINUTES))

  }

}