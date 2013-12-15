package com.blrest.boot

import com.typesafe.scalalogging.slf4j.Logging
import akka.actor.{Props, ActorSystem}
import spray.can.Http
import akka.io.IO
import com.blrest.endpoint.ImageDirectoryEndpoint
import reactivemongo.api._
import scala.concurrent.ExecutionContext.Implicits.global
import com.typesafe.config.ConfigFactory
import scala.util.Properties
import com.blrest.mongo.ReactiveMongoConnection

/**
 * Created by ccarrier for bl-rest.
 * at 9:32 PM on 12/14/13
 */
object Boot extends App with Logging with ReactiveMongoConnection {

  implicit val system = ActorSystem()

  private val config = ConfigFactory.load()

  val host = "0.0.0.0"
  val port = Properties.envOrElse("PORT", "8080").toInt

  // the handler actor replies to incoming HttpRequests
  val handler = system.actorOf(Props[ImageDirectoryEndpoint], name = "imageDirectoryEndpoint")

  IO(Http) ! Http.Bind(handler, interface = host, port = port)
}
