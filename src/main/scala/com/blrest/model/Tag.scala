package com.blrest.model

import reactivemongo.bson.BSONObjectID

/**
 * Created by ccarrier for bl-rest.
 * at 1:44 PM on 12/20/13
 */
case class Predicate(key: String)
case object Predicate {
  def IS = Predicate("IS")
  def HAS = Predicate("HAS")
}
case class Tag(_id: Option[BSONObjectID], name: String, displayPattern: String, predicate: Predicate)

case class TagResponse(_id: Option[BSONObjectID], tag: Tag, imageMeta: ImageMeta, response: Boolean)