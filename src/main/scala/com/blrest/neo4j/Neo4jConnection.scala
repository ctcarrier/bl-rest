package com.blrest.neo4j

import scala.util.Properties
import com.typesafe.config.ConfigFactory
import com.blrest.dao.Neo4jDao
import com.typesafe.scalalogging.slf4j.Logging
import java.net.URI

/**
 * Created by ctcarrier on 12/25/13.
 */
trait Neo4jConnection extends Logging {

  private val config = ConfigFactory.load()

  val neoUri: String = Properties.envOrElse("NEO4J_BASIC_URL", config.getString("blrest.neo4j.uri")).toString

  val neoUserTuple: Option[(String, String)] = (Properties.envOrNone("NEO4J_USER"), Properties.envOrNone("NEO4J_PASS")) match {
    case (Some(x), Some(y)) => Some(x.toString, y.toString)
    case _ => None
  }
}
