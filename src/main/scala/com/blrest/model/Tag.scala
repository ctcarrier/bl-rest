package com.blrest.model

/**
 * Created by ccarrier for bl-rest.
 * at 1:44 PM on 12/20/13
 */
case class Predicate(key: String)
case object Predicate {
  def IS = Predicate("IS")
  def HAS = Predicate("HAS")
}
case class Tag(name: String, displayPattern: String, predicate: Predicate)

case class TagResponse(tag: Tag, response: Boolean)