package com.blrest.endpoint

import akka.actor.Actor
import spray.routing.HttpService
import spray.http.MediaTypes._
import com.typesafe.scalalogging.slf4j.Logging
import spray.httpx.Json4sJacksonSupport
import scala.concurrent.ExecutionContext
import org.json4s.DefaultFormats
import com.codahale.metrics.MetricRegistry
import com.blrest.boot.Instrumented

/**
 * Created by ccarrier for bl-rest.
 * at 5:35 PM on 12/17/13
 */

trait MetricsActor extends Actor with MetricsEndpoint {

  def actorRefFactory = context

  def receive = runRoute(metricsRoute)
}

trait MetricsEndpoint extends HttpService with Logging with Json4sJacksonSupport with Instrumented {

  import ExecutionContext.Implicits.global

  def metricsRoute =
    respondWithMediaType(`application/json`) {
      path("metrics") {
          get {
            complete {
              metricRegistry.counter("getDirect")
            }
          }
      }
    }

}