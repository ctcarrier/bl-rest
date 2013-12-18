package com.blrest.model

import reactivemongo.bson.{BSONDocumentReader, BSONDocument}

/**
 * Created by ccarrier for bl-rest.
 * at 2:53 PM on 12/15/13
 */
case class ImageMeta(
                      book_identifier: Long,
                      title: String,
                      first_author: String,
                      pubplace: String,
                      publisher: String,
                      date: String,
                      volume: Int,
                      page: Int,
                      image_idx: Int,
                      ARK_id_of_book: String,
                      BL_DLS_ID: String,
                      flickr: FlickrData
                      )

case class FlickrData(flickr_id: Long,
                      flickr_url: String,
                      flickr_small_source: String,
                      flickr_small_height: Int,
                      flickr_small_width: Int,
                      flickr_medium_source: String,
                      flickr_medium_height: Int,
                      flickr_medium_width: Int,
                      flickr_large_source: String,
                      flickr_large_height: Int,
                      flickr_large_width: Int,
                      flickr_original_source: String,
                      flickr_original_height: Int,
                      flickr_original_width: Int)

