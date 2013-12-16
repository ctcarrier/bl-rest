package com.blrest

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import com.blrest.endpoint.ImageDirectoryEndpoint
import com.blrest.dao.ImageDirectoryDao
import com.blrest.model.ImageMeta
import scala.util.Random
import spray.http.StatusCodes._
import spray.http.ContentTypes.`application/json`

import org.json4s._
import org.json4s.jackson.JsonMethods._
import scala.concurrent.Future

/**
 * Created by ccarrier for bl-rest.
 * at 10:07 PM on 12/15/13
 */
class ImageDirectoryEndpointSpec extends Specification with Specs2RouteTest with ImageDirectoryEndpoint {

  def actorRefFactory = system
  implicit val formats = DefaultFormats

  val dummyImageMeta = ImageMeta(
    Random.nextLong(),
    "url",
    Random.nextLong(),
    "title",
    "first_author",
    "pubplace",
    "publisher",
    "date",
    Random.nextInt(),
    Random.nextInt(),
    Random.nextInt(),
    "ARK_id_of_book",
    "BL_DLS_ID"

  )

  "The service" should {

    "return an ImageMeta for direct GET requests" in {
      Get("/images/%s".format(dummyImageMeta.flickr_id)) ~> imageDirectoryRoute ~> check {
        responseAs[ImageMeta] == dummyImageMeta
        contentType === `application/json`
        status === OK
      }
    }
    "return a 404 for a non-existant ImageMeta for GET requests" in {
      Get("/images/%s".format(000)) ~> imageDirectoryRoute ~> check {
        status === NotFound
      }
    }
    "return a random ImageMeta for GET requests" in {
      Get("/images/random") ~> imageDirectoryRoute ~> check {
        responseAs[ImageMeta] == dummyImageMeta
        contentType === `application/json`
        status === OK
      }
    }
  }
  val imageDirectoryDao = new ImageDirectoryDao {
    def getImageMetaData(key: Long): Future[Option[ImageMeta]] = {
      if (key == dummyImageMeta.flickr_id) {
        return Future.successful(Some(dummyImageMeta))
      }
      else {
        return Future.successful(None)
      }
    }

    def getRandomImageMetaData: Future[Option[ImageMeta]] = {
      return Future.successful(Some(dummyImageMeta))
    }
  }

}
