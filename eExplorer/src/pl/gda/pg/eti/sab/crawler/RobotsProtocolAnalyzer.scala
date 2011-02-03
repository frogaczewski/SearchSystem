package pl.gda.pg.eti.sab.crawler

import collection.mutable
import java.util.concurrent.Executors
import actors.Actor
import pl.gda.pg.eti.sab.util.{Logging, CrawlerPropertiesReader}

/**
 * 
 * @author Filip Rogaczewski
 */
object RobotsProtocolAnalyzer extends Actor with Logging {

	/**
	 * Map with all pages excluded by robots.txt files.
	 */
	val excluded = new mutable.HashSet[String]

	/**
	 * List of hosts analyzed for robots protocol.
	 */
	val robotsAnalyzed = new mutable.HashSet[String]

	/**
	 * Robots protocol not indexed.
	 */
	val robotsDisallow = "Disallow: "

	/**
	 * Robots protocol thread pool.
	 */
	val threadPool = Executors.newFixedThreadPool(CrawlerPropertiesReader.robotsThreadPoolSize)

	def act() {
		loop {
			react {
				case exclusions : mutable.HashSet[String] => {
					log.info("New robots protocol exclusions " + exclusions)
					excluded ++= exclusions
				}
				case page : PageEntity => {
					analyze(page.url)
				}
			}
		}
	}


	def analyze(url : String) = {
		val host = hostname(url)
		if (!robotsAnalyzed.contains(host)) {
			log.info("Analyzing robots protocol for host " + host)
			robotsAnalyzed += host
			threadPool.execute(new RobotsProtocolAnalyzerTask(host))
		}
	}

	def isNotExcluded(url : String) = {
		!excluded.contains(url)
	}

	/**
	 * Returns hostname of the url.
	 */
	private def hostname(url : String) = {
		"http://" + url.split("/")(2)
	}
}