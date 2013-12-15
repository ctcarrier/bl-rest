package com.blrest.dao

import reactivemongo.api.Collection
import reactivemongo.bson.BSONDocument
import reactivemongo.api.collections.default.BSONCollection
import scala.concurrent.Future

/**
 * Created by ccarrier for bl-rest.
 * at 10:00 PM on 12/14/13
 */
trait ImageDirectoryDao {

  def getImageMetaData(key: Long): Future[Option[BSONDocument]]

}

class ImageDirectoryReactiveDao(imageCollection: BSONCollection) extends ImageDirectoryDao {

  def getImageMetaData(key: Long): Future[Option[BSONDocument]] = {
    val query = BSONDocument("flickr_id" -> key)
    imageCollection.find(query).one[BSONDocument]
  }
}