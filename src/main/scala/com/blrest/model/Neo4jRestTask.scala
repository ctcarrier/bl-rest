package com.blrest.model

/**
 * Created by ctcarrier on 12/25/13.
 */

object Neo4jRestMethod {

  val POST = "POST"
  val GET = "GET"
}

object Neo4jRestTo {

  val NODE = "/node"
  val LABEL = "/node/101/labels"
}

case class Neo4jRestTask(method: String, to: String, id: Int, body: AnyRef)
