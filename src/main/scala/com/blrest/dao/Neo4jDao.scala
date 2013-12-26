package com.blrest.dao

import eu.fakod.neo4jscala.{DatabaseServiceImpl, DatabaseService, RestGraphDatabaseServiceProvider, Neo4jWrapper}
import com.blrest.model._
import java.net.URI
import org.neo4j.rest.graphdb.RestGraphDatabase
import akka.actor.Actor

/**
 * Created by ctcarrier on 12/24/13.
 */
class Neo4jDao(_uri: String, neoUserTuple: Option[(String, String)]) extends Neo4jWrapper with RestGraphDatabaseServiceProvider with Actor {

  override def uri = URI.create(_uri)
  override def userPw = neoUserTuple

  def receive = {
    case x: TagResponse => saveTag(x)
  }

  def saveTag(resp: TagResponse) {
    if (resp.response){
      withTx {
        implicit neo =>
          val start = createNode
          start("book_identifier") = resp.imageMeta.book_identifier
          start("date") = resp.imageMeta.date
          start("first_author") = resp.imageMeta.first_author
          start("image_idx") = resp.imageMeta.image_idx
          start("page") = resp.imageMeta.page
          start("publisher") = resp.imageMeta.publisher
          start("pubplace") = resp.imageMeta.pubplace
          start("title") = resp.imageMeta.title
          start("volume") = resp.imageMeta.volume
          start("ARK_id_of_book") = resp.imageMeta.ARK_id_of_book
          start("BL_DLS_ID") = resp.imageMeta.BL_DLS_ID
          start("flickr_id") = resp.imageMeta.flickr.flickr_id
          val end = createNode
        end("name") = resp.tag.name
          start --> resp.tag.predicate.key --> end
      }
    }
  }

}
