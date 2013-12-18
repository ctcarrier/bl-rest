package com

import reactivemongo.bson.{Macros, BSONDocument, BSONDocumentReader}
import com.blrest.model.{FlickrData, ImageMeta}

/**
 * Created by ccarrier for bl-rest.
 * at 3:19 PM on 12/15/13
 */
package object blrest {

  implicit val flickrHandler = Macros.handler[FlickrData]
  implicit val imageMetaHandler = Macros.handler[ImageMeta]
}
