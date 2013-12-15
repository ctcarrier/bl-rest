package com.blrest.mongo

import reactivemongo.api.MongoDriver
import com.typesafe.config.ConfigFactory


/**
 * Created by ccarrier for bl-rest.
 * at 10:02 PM on 12/14/13
 */
trait ReactiveMongoConnection {

  private val config = ConfigFactory.load()

  val driver = new MongoDriver
  val connection = driver.connection(List(config.getString("mongodb.url")))

  // Gets a reference to the database "plugin"
  val db = connection(config.getString("mongodb.database"))

  // Gets a reference to the collection "acoll"
  // By default, you get a BSONCollection.
  val imageCollection = db(config.getString("blrest.image.collection"))

}
