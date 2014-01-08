package com.blrest.spray

import spray.routing._
import scala.Some
import scala.Some
import reactivemongo.bson.BSONObjectID

/**
 * Created by ctcarrier on 1/7/14.
 */
trait BlPathMatchers {

  val BSONObjectID: PathMatcher1[BSONObjectID] =
    PathMatcher("""[\da-fA-F]""".r) flatMap { string ⇒
      try Some(new BSONObjectID(string))
      catch { case _: IllegalArgumentException ⇒ None }
    }
}
