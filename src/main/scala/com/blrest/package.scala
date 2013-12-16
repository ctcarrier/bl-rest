package com

import reactivemongo.bson.{BSONDocument, BSONDocumentReader}
import com.blrest.model.ImageMeta

/**
 * Created by ccarrier for bl-rest.
 * at 3:19 PM on 12/15/13
 */
package object blrest {

  implicit object ImageMetaReader extends BSONDocumentReader[ImageMeta] {
    def read(doc: BSONDocument): ImageMeta = {
      ImageMeta(
        doc.getAs[Long]("flickr_id").get,
        doc.getAs[String]("flickr_url").get,
        doc.getAs[Long]("book_identifier").get,
        doc.getAs[String]("title").get,
        doc.getAs[String]("first_author").get,
        doc.getAs[String]("pubplace").get,
        doc.getAs[String]("publisher").get,
        doc.getAs[String]("date").get,
        doc.getAs[Int]("volume").get,
        doc.getAs[Int]("page").get,
        doc.getAs[Int]("image_idx").get,
        doc.getAs[String]("ARK_id_of_book").get,
        doc.getAs[String]("BL_DLS_ID").get

      )
    }
  }
}
