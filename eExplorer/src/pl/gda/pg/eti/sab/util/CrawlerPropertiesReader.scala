package pl.gda.pg.eti.sab.util

import java.util.Properties

/**
 * 
 * @author Filip Rogaczewski
 */
object CrawlerPropertiesReader {

	private val CRAWLER_PROPERTIES_FILE = "/crawler.properties"

	lazy val properties : Properties = {
		val stream = this.getClass.getResourceAsStream(CRAWLER_PROPERTIES_FILE)
		val props = new Properties
		props.load(stream)
		props
	}

	def crawlerDepth = properties.get("pl.gda.eti.sab.crawler.depth").asInstanceOf[String].toInt
	def crawlerStartPage = properties.get("pl.gda.eti.sab.crawler.startpage").asInstanceOf[String]
	def crawlerThreadPoolSize = properties.get("pl.gda.eti.sab.crawler.threadpool.size").asInstanceOf[String].toInt
	def crawlerTimeout = properties.get("pl.gda.eti.sab.crawler.thread.timeout").asInstanceOf[String].toLong
	def connectionTimeout = properties.get("pl.gda.eti.sab.crawler.connection.timeout").asInstanceOf[String].toLong
	def indexDirectory = properties.get("pl.gda.eti.sab.indexer.index.dir").asInstanceOf[String]
	def searchFuzzyFactor = properties.get("pl.gda.eti.sab.searcher.search.fuzzy.factor").asInstanceOf[String]
	def dampingFactor = properties.get("pl.gda.eti.sab.ranker.damping.factor").asInstanceOf[String].toDouble
	def rankerThreadPoolSize = properties.get("pl.gda.eti.sab.ranker.threadpool.size").asInstanceOf[String].toInt
	def serverPort = properties.get("pl.gda.eti.sab.server.port").asInstanceOf[String].toInt
	def crawlerRobotsProtocol = properties.get("pl.gda.eti.sab.crawler.robots").asInstanceOf[String]
	def rankerIterations = properties.get("pl.gda.eti.sab.ranker.iterations").asInstanceOf[String].toInt

}