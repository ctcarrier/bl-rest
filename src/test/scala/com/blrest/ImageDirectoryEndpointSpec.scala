package com.blrest

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import com.blrest.endpoint.ImageDirectoryEndpoint
import com.blrest.dao.ImageDirectoryDao
import org.scalamock.specs2.MockFactory
import com.blrest.model.ImageMeta
import scala.util.Random

/**
 * Created by ccarrier for bl-rest.
 * at 10:07 PM on 12/15/13
 */
class ImageDirectoryEndpointSpec extends Specification with Specs2RouteTest with ImageDirectoryEndpoint with MockFactory {

  def actorRefFactory = system
  
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

    "return a greeting for GET requests to the root path" in {
      Get() ~> imageDirectoryRoute ~> check {
        responseAs[String] must contain("Say hello")
      }
    }
  }
  val mockDao: ImageDirectoryDao = mock[ImageDirectoryDao]

  mockDao expects 'getImageMetaData withArgs(123l)

}
