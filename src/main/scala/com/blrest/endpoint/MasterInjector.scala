package com.blrest.endpoint

import akka.actor.Actor
import com.codahale.metrics.MetricRegistry
import com.blrest.dao.{TagDao, ImageDirectoryDao}
import org.json4s.DefaultFormats
import spray.routing.RejectionHandler

/**
 * Created by ccarrier for bl-rest.
 * at 5:54 PM on 12/17/13
 */


trait MasterInjector extends Actor with ImageDirectoryEndpoint with TagEndpoint {

  val imageDirectoryDao: ImageDirectoryDao
  val tagDao: TagDao

  val json4sJacksonFormats = DefaultFormats

  def actorRefFactory = context

  def receive = runRoute(imageDirectoryRoute ~ tagRoute)
}
