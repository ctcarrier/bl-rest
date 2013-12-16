package com.blrest.dao

import scala.concurrent.Future
import akka.actor.ActorSystem

import reactivemongo.bson.BSONDocument
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.api.{QueryOpts, DB}
import reactivemongo.core.commands.Count
import reactivemongo.api.collections.default._

import com.typesafe.scalalogging.slf4j.Logging

import com.blrest._
import com.blrest.model.ImageMeta

import scala.util.Random

/**
 * Created by ccarrier for bl-rest.
 * at 10:00 PM on 12/14/13
 */
trait ImageDirectoryDao {

  def getImageMetaData(key: Long): Future[Option[ImageMeta]]
  def getRandomImageMetaData: Future[Option[ImageMeta]]

}

class ImageDirectoryReactiveDao(db: DB, imageCollection: BSONCollection, system: ActorSystem) extends ImageDirectoryDao with Logging {

  implicit val context = system.dispatcher

  def getImageMetaData(key: Long): Future[Option[ImageMeta]] = {
    logger.debug("Getting image: %s".format(key))
    val query = BSONDocument("flickr_id" -> key)
    imageCollection.find(query).one[ImageMeta]
  }

  def getRandomImageMetaData: Future[Option[ImageMeta]] = {
    val futureCount = db.command(Count(imageCollection.name))
    futureCount.flatMap { count =>
      val skip = Random.nextInt(count)
      imageCollection.find(BSONDocument()).options(QueryOpts(skipN = skip)).one[ImageMeta]
    }
  }
}