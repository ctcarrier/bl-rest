package com.blrest.dao

import eu.fakod.neo4jscala.{RestGraphDatabaseServiceProvider, Neo4jWrapper}
import com.blrest.model.TagResponse
import java.net.URI

/**
 * Created by ctcarrier on 12/24/13.
 */
class Neo4jDao(_uri: String) extends Neo4jWrapper with RestGraphDatabaseServiceProvider {

  val uri = new URI(_uri)

  def saveTag(resp: TagResponse) {
    withTx {
      implicit neo =>
        val start = createNode
        val end = createNode
        start --> "foo" --> end
    }
  }
}
