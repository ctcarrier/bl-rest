package com.blrest.endpoint

import akka.actor.Actor
import com.codahale.metrics.MetricRegistry
import com.blrest.dao.ImageDirectoryDao
import org.json4s.DefaultFormats

/**
 * Created by ccarrier for bl-rest.
 * at 5:54 PM on 12/17/13
 */

trait MasterInjector extends Actor with MetricsEndpoint with ImageDirectoryEndpoint {

  val imageDirectoryDao: ImageDirectoryDao

  val json4sJacksonFormats = DefaultFormats

  def actorRefFactory = context

  def receive = runRoute(imageDirectoryRoute ~ metricsRoute)
}
