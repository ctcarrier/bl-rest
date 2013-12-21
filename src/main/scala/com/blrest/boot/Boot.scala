package com.blrest.boot

import com.typesafe.scalalogging.slf4j.Logging
import akka.actor.{Actor, IndirectActorProducer, Props, ActorSystem}
import spray.can.Http
import akka.io.IO
import com.blrest.endpoint.{MasterInjector, MetricsActor, ImageDirectoryActor}
import com.typesafe.config.ConfigFactory
import scala.util.Properties
import com.blrest.mongo.ReactiveMongoConnection
import com.blrest.dao.{MongoTagDao, TagDao, ImageDirectoryReactiveDao, ImageDirectoryDao}
import akka.util.Timeout
import scala.concurrent.duration._
import akka.pattern.ask
import com.codahale.metrics.{MetricFilter, Slf4jReporter, ConsoleReporter, MetricRegistry}
import java.util.concurrent.TimeUnit
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import com.codahale.metrics.graphite.{GraphiteReporter, Graphite}

/**
 * Created by ccarrier for bl-rest.
 * at 9:32 PM on 12/14/13
 */

trait MyActorSystem {

  implicit val system = ActorSystem()
}

trait Instrumented extends nl.grons.metrics.scala.InstrumentedBuilder {
  val metricRegistry = Boot.metricRegistry
}

class DependencyInjector(dao: ImageDirectoryDao, _tagDao: TagDao)
  extends IndirectActorProducer {

  override def actorClass = classOf[Actor]
  override def produce = new MasterInjector{
    val imageDirectoryDao = dao
    val tagDao = _tagDao
  }
}

object Boot extends App with Logging with ReactiveMongoConnection with MyActorSystem {

  val metricRegistry = new MetricRegistry()
  private val config = ConfigFactory.load()

  if (config.getBoolean("blrest.metrics.enabled")) {

    //val graphite = new Graphite(new InetSocketAddress(config.getString("blrest.metrics.host"), config.getInt("blrest.metrics.port")))
    val graphite = new Graphite(new InetSocketAddress("carbon.hostedgraphite.com", 2003))
    val reporter = GraphiteReporter.forRegistry(metricRegistry)
      .prefixedWith("%s.blrest".format(Properties.envOrElse("GRAPHITE_KEY", "FAIL")))
      .convertRatesTo(TimeUnit.SECONDS)
      .convertDurationsTo(TimeUnit.MILLISECONDS)
      .filter(MetricFilter.ALL)
      .build(graphite)

    reporter.start(5, TimeUnit.SECONDS)
  }


  val host = "0.0.0.0"
  val port = Properties.envOrElse("PORT", "8080").toInt
  val flickrApiKey = Properties.envOrElse("FLICKR_API_KEY", "")

  private val imageDirectoryDao: ImageDirectoryDao = new ImageDirectoryReactiveDao(db, imageCollection, system)
  private val tagDao: TagDao = new MongoTagDao(db, tagCollection, tagResponseCollection, system)

  // the handler actor replies to incoming HttpRequests
  val handler = system.actorOf(Props(classOf[DependencyInjector], imageDirectoryDao, tagDao), name = "endpoints")

  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(handler, interface = host, port = port)
}
