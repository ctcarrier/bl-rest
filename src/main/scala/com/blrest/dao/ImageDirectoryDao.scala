package com.blrest.dao

import scala.concurrent.Future
import akka.actor.ActorSystem
import reactivemongo.bson.BSONDocument
import reactivemongo.api.collections.default.BSONCollection
import com.typesafe.scalalogging.slf4j.Logging

/**
 * Created by ccarrier for bl-rest.
 * at 10:00 PM on 12/14/13
 */
trait ImageDirectoryDao {

  def getImageMetaData(key: Long): Future[Option[BSONDocument]]

}

class ImageDirectoryReactiveDao(imageCollection: BSONCollection, system: ActorSystem) extends ImageDirectoryDao with Logging {

  implicit val context = system.dispatcher

  def getImageMetaData(key: Long): Future[Option[BSONDocument]] = {
    logger.debug("Getting image: %s".format(key))
    val query = BSONDocument("flickr_id" -> key)
    imageCollection.find(query).one[BSONDocument]
  }
}