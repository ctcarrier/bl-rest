package com.blrest.dao

import scala.concurrent.Future
import akka.actor.ActorSystem

import reactivemongo.bson.{BSONObjectID, BSONDocument}
import reactivemongo.api.{QueryOpts, DB}
import reactivemongo.core.commands.Count
import reactivemongo.api.collections.default._

import com.typesafe.scalalogging.slf4j.Logging

import com.blrest._
import com.blrest.model.ImageMeta

import scala.util.Random
import com.blrest.boot.Instrumented
import nl.grons.metrics.scala.Counter

/**
 * Created by ccarrier for bl-rest.
 * at 10:00 PM on 12/14/13
 */
trait ImageDirectoryDao {

  def getImageMetaData(key: BSONObjectID): Future[Option[ImageMeta]]
  def getRandomImageMetaData: Future[Option[ImageMeta]]
  def getImageMetaDataByFlickrId(key: Long): Future[Option[ImageMeta]]

}

class ImageDirectoryReactiveDao(db: DB, imageCollection: BSONCollection, system: ActorSystem) extends ImageDirectoryDao with Logging {

  implicit val context = system.dispatcher

  def getImageMetaData(key: BSONObjectID): Future[Option[ImageMeta]] = {
    logger.debug("Getting image: %s".format(key))
    imageCollection.find(BSONDocument("_id" -> key)).one[ImageMeta]
  }

  def getImageMetaDataByFlickrId(key: Long): Future[Option[ImageMeta]] = {
    logger.debug("Getting image: %s".format(key))
    imageCollection.find(BSONDocument("flickr.flickr_id" -> key)).one[ImageMeta]
  }

  def getRandomImageMetaData: Future[Option[ImageMeta]] = {
    val futureCount = db.command(Count(imageCollection.name))
    futureCount.flatMap { count =>
      val skip = Random.nextInt(count)
      imageCollection.find(BSONDocument()).options(QueryOpts(skipN = skip)).one[ImageMeta]
    }
  }
}