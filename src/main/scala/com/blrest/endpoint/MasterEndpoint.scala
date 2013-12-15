package com.blrest.endpoint

import akka.actor.Actor
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization

/**
 * Created by ccarrier for bl-rest.
 * at 8:57 PM on 12/14/13
 */
trait MasterEndpoint extends Actor {

  implicit def actorRefFactory = context
  implicit def executor = context.dispatcher

  implicit val json4sJacksonFormats = Serialization.formats(NoTypeHints)


}
