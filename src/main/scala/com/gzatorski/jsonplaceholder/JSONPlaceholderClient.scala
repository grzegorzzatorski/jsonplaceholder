package com.gzatorski.jsonplaceholder


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.util.ByteString
import com.gzatorski.jsonplaceholder.model.{JsonParser, Post}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class JSONPlaceholderClient(ac: ActorSystem) {

  implicit val actorSystem = ac
  implicit val materializer = ActorMaterializer()

  def getAllPosts(requestURL: String): Future[List[Post]] = {
    val httpRequest = HttpRequest(uri = requestURL)

    Http().singleRequest(httpRequest)
      .flatMap {
        extractData(_).runWith(Sink.head)
      }
      .map(_.utf8String)
      .map(JsonParser.getAsPosts)
  }

  private def extractData(response: HttpResponse): Source[ByteString, _] =
    response match {
      case HttpResponse(OK, _, entity, _) => entity.dataBytes
      case resp => Source.failed(new RuntimeException(s"Bad http response $resp"))
    }

}