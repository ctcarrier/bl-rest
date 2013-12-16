package com.blrest.model

import reactivemongo.bson.{BSONDocumentReader, BSONDocument}

/**
 * Created by ccarrier for bl-rest.
 * at 2:53 PM on 12/15/13
 */
case class ImageMeta(flickr_id: Long,
flickr_url: String,
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
BL_DLS_ID: String)

