package com.blrest.endpoint

import spray.http._
import MediaTypes._

import com.typesafe.scalalogging.slf4j.Logging

import spray.routing.HttpService
import spray.httpx.Json4sJacksonSupport

import akka.actor.Actor

import com.blrest.dao.ImageDirectoryDao
import com.blrest.model.ImageMeta

import org.json4s.DefaultFormats
import scala.concurrent.ExecutionContext
import com.blrest.spray.BlPathMatchers

/**
 * Created by ccarrier for bl-rest.
 * at 9:00 PM on 12/14/13
 */

trait ImageDirectoryActor extends Actor with ImageDirectoryEndpoint {

  val imageDirectoryDao: ImageDirectoryDao

  def actorRefFactory = context

  def receive = runRoute(imageDirectoryRoute)
}

trait ImageDirectoryEndpoint extends HttpService with Logging with Json4sJacksonSupport with BlPathMatchers {

  import ExecutionContext.Implicits.global

  val imageDirectoryDao: ImageDirectoryDao

  def imageDirectoryRoute =
    respondWithMediaType(`application/json`) {
      pathPrefix("images") {
        path(BSONObjectID) { key =>
          get {
            complete {
              imageDirectoryDao.getImageMetaData(key)
            }
          }
        } ~
        path("random"){
          get {
            complete {
              imageDirectoryDao.getRandomImageMetaData.mapTo[Option[ImageMeta]]
            }
          }
        }
      }
    }

}
