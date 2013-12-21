package com

import reactivemongo.bson.{Macros, BSONDocument, BSONDocumentReader}
import com.blrest.model._
import com.blrest.model.TagResponse
import com.blrest.model.FlickrData
import com.blrest.model.Tag
import com.blrest.model.ImageMeta

/**
 * Created by ccarrier for bl-rest.
 * at 3:19 PM on 12/15/13
 */
package object blrest {

  implicit val flickrHandler = Macros.handler[FlickrData]
  implicit val imageMetaHandler = Macros.handler[ImageMeta]

  implicit val predicateHandler = Macros.handler[Predicate]
  implicit val tagHandler = Macros.handler[Tag]
  implicit val tagResponseHandler = Macros.handler[TagResponse]
}
