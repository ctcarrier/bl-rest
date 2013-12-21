package com.blrest.endpoint

import com.typesafe.scalalogging.slf4j.Logging

import com.blrest.boot.Instrumented

import scala.concurrent.ExecutionContext

import spray.routing.HttpService
import spray.http.MediaTypes._
import spray.httpx.Json4sJacksonSupport
import spray.http.StatusCodes._

import com.blrest.model.TagResponse
import com.blrest.dao.TagDao
import spray.http.HttpResponse

/**
 * Created by ccarrier for bl-rest.
 * at 1:58 PM on 12/20/13
 */
trait TagEndpoint extends HttpService with Logging with Json4sJacksonSupport with Instrumented {

  import ExecutionContext.Implicits.global

  val postTagResponse = post & entity(as[TagResponse]) & respondWithStatus(Created)

  val tagDao: TagDao

  def tagRoute =
    respondWithMediaType(`application/json`) {
      path("tags" / "random") {
        get {
          complete {
            tagDao.getRandomTag
          }
        }
      } ~
      path("tagResponses") {
        postTagResponse { tagResponse =>
          complete {
            tagDao.saveTagResponse(tagResponse)
          }
        }
      }
    }

}