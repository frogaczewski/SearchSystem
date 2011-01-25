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
	def indexDirectory = properties.get("pl.gda.eti.sab.crawler.index.dir").asInstanceOf[String]

}