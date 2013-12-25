package com.blrest.dao

import com.blrest.model.{Tag, TagResponse}
import reactivemongo.api.{QueryOpts, DB}
import reactivemongo.api.collections.default.BSONCollection
import akka.actor.ActorSystem
import reactivemongo.core.commands.Count
import scala.util.Random
import reactivemongo.bson.BSONDocument
import scala.concurrent.Future
import com.typesafe.scalalogging.slf4j.Logging

/**
 * Created by ccarrier for bl-rest.
 * at 2:05 PM on 12/20/13
 */
trait TagDao {

  def getRandomTag: Future[Option[Tag]]
  def saveTagResponse(tagResponse: TagResponse): Either[Exception, TagResponse]
}

class MongoTagDao(db: DB, tagCollection: BSONCollection, tagResponseCollection: BSONCollection, system: ActorSystem) extends TagDao with Logging {

  implicit val context = system.dispatcher

  def getRandomTag: Future[Option[Tag]] = {
    val futureCount = db.command(Count(tagCollection.name))
    futureCount.flatMap { count =>
      val skip = Random.nextInt(count)
      tagCollection.find(BSONDocument()).options(QueryOpts(skipN = skip)).one[Tag]
    }

  }
  def saveTagResponse(tagResponse: TagResponse): Either[Exception, TagResponse] = {

    tagResponseCollection.insert(tagResponse)
    Right(tagResponse)
  }
}