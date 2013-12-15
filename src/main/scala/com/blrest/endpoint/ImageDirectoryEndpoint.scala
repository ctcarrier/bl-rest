package com.blrest.endpoint

import spray.http._
import MediaTypes._
import com.typesafe.scalalogging.slf4j.Logging
import spray.routing.HttpService
import spray.httpx.Json4sJacksonSupport
import akka.actor.{ActorContext, Actor}
import com.blrest.json.BLRestJacksonFormats
import com.blrest.dao.ImageDirectoryDao
import scala.util.Success
import scala.concurrent.{ExecutionContext, Future}
import spray.httpx.marshalling.Marshaller
import org.json4s.DefaultFormats

/**
 * Created by ccarrier for bl-rest.
 * at 9:00 PM on 12/14/13
 */

trait ImageDirectoryActor extends Actor with ImageDirectoryEndpoint {

  val imageDirectoryDao: ImageDirectoryDao

  def actorRefFactory = context

  def receive = runRoute(imageDirectoryRoute)
}

trait ImageDirectoryEndpoint extends HttpService with Logging with Json4sJacksonSupport {

  implicit val context: ExecutionContext
  val imageDirectoryDao: ImageDirectoryDao

  val json4sJacksonFormats = DefaultFormats

  def imageDirectoryRoute =
    respondWithMediaType(`application/json`) {
      path("images" / LongNumber){ key =>

        get {
          complete {
            imageDirectoryDao.getImageMetaData(key) onComplete  { resp =>
              ////              resp match {
////              case Success(Some(x)) => x
////              case _ => spray.http.StatusCodes.NotFound
////            }
              "Test"
          }
          }
        }
      }
    }

}
