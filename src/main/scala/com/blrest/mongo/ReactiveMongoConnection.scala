package com.blrest.mongo

import reactivemongo.api.MongoDriver
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import com.blrest.boot.MyActorSystem
import scala.util.Properties
import scala.concurrent.duration._
import scala.concurrent.Await
import com.typesafe.scalalogging.slf4j.Logging


/**
 * Created by ccarrier for bl-rest.
 * at 10:02 PM on 12/14/13
 */
trait ReactiveMongoConnection extends MyActorSystem with Logging {

  private val config = ConfigFactory.load()
  implicit val context = system.dispatcher
  val driver = new MongoDriver

  val pattern = "^mongodb:\\/\\/([\\w]*):([\\w]*)[@].([\\w\\.]+):([\\d]+)\\/([\\w]+)".r

  val envUri = Properties.envOrElse("MONGOLAB_URI", "").toString

  val (connection, db) = if (!envUri.isEmpty){
    logger.info("Attempting to parse: %s".format(envUri))
    val pattern(user, password, host, port, dbName) = envUri

    val connection = driver.connection(List("%s:%s".format(host, port)))

    // Gets a reference to the database "plugin"
    val db = connection(dbName)
    val authResult = Await.result(db.authenticate(user, password), 120.seconds)

    (connection, db)

  }
  else {
    val connection = driver.connection(List(config.getString("mongodb.url")))

    // Gets a reference to the database "plugin"
    val db = connection(config.getString("mongodb.database"))

    (connection, db)
  }

  // Gets a reference to the collection "acoll"
  // By default, you get a BSONCollection.
  val imageCollection = db(config.getString("blrest.image.collection"))

}
