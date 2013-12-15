package com.blrest.json

import org.json4s.jackson.Serialization
import org.json4s.NoTypeHints

/**
 * Created by ccarrier for bl-rest.
 * at 9:58 PM on 12/14/13
 */
trait BLRestJacksonFormats {

  implicit val json4sJacksonFormats = Serialization.formats(NoTypeHints)

}
