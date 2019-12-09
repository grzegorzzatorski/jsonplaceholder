package com.gzatorski.jsonplaceholder.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.{ExecutionContext, Future}

class Client(ac: ActorSystem) extends LazyLogging {

  implicit val actorSystem: ActorSystem = ac
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  def performRequest(request: HttpRequest)(implicit ec: ExecutionContext): Future[String] = {

    val sink = Sink.fold[String, ByteString]("") { case (sum, byteStr) =>
      sum + byteStr.utf8String
    }

    Http().singleRequest(request)
      .flatMap {
        extractData(_).runWith(sink)
      }
  }

  private def extractData(response: HttpResponse): Source[ByteString, _] =
    response match {
      case HttpResponse(OK, _, entity, _) => entity.dataBytes
      case resp => Source.failed(new RuntimeException(s"Bad http response $resp"))
    }

}