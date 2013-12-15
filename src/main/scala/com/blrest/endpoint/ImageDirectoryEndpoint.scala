package com.blrest.endpoint

import spray.http._
import MediaTypes._
import HttpMethods._
import spray._
import com.typesafe.scalalogging.slf4j.Logging
import spray.routing.HttpService
import spray.httpx.Json4sJacksonSupport
import akka.actor.Actor
import com.blrest.json.BLRestJacksonFormats

/**
 * Created by ccarrier for bl-rest.
 * at 9:00 PM on 12/14/13
 */

class ImageDirectoryActor extends Actor with ImageDirectoryEndpoint with BLRestJacksonFormats {

  def actorRefFactory = context

  def receive = runRoute(imageDirectoryRoute)
}

trait ImageDirectoryEndpoint extends HttpService with Logging with Json4sJacksonSupport {

  def imageDirectoryRoute =
    respondWithMediaType(`application/json`) {
      path("images" / Segment){ key =>

        get {
          complete {

          }
        }
      }
    }

}
