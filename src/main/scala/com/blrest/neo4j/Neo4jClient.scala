package com.blrest.neo4j

import spray.http._
import ContentType._
import MediaTypes._
import spray.client.pipelining._
import scala.concurrent.Future
import akka.actor.ActorSystem
import com.blrest.model.Neo4jNode
import spray.httpx.Json4sJacksonSupport
import org.json4s.Formats

/**
 * Created by ctcarrier on 12/25/13.
 */
trait Neo4jClient {
}

class Neo4jSprayClient(uri: String)(implicit val system: ActorSystem, implicit val json4sJacksonFormats: Formats) extends Neo4jClient with Json4sJacksonSupport {

  import system.dispatcher // execution context for futures

  val pipeline: HttpRequest => Future[Neo4jNode] = (
    addHeader("Accept", `application/json`.toString())
      ~> addCredentials(BasicHttpCredentials("bob", "secret"))
      ~> sendReceive
      ~> unmarshal[Neo4jNode]
    )

  def createNode() {

    val response: Future[Neo4jNode] = pipeline(Get(uri))
  }
}