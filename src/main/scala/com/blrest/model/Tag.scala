package com.blrest.model

/**
 * Created by ccarrier for bl-rest.
 * at 1:44 PM on 12/20/13
 */
case class Predicate(displayPattern: String, key: String)
case class Tag(name: String, predicate: Predicate)

case class TagResponse(tag: Tag, response: Boolean)