package pl.gda.pg.eti.sab.crawler

import org.apache.log4j.Logger
import pl.gda.pg.eti.sab.util.Logging

/**
 * 
 * @author Filip Rogaczewski
 */
class CrawlerTask(val url : String, val depth : Int) extends Runnable with Ordered[CrawlerTask] with Logging {

	def run() : Unit = {
		try {
			log.info("Crawling " + url + " depth " + depth)
			val page = PageFactory(url, depth)
			page match {
				case Some(value) => Crawler ! value
				case None => {}
			}
		} catch {
			case e: Exception => {
				log.error("Exception crawling " + url)
			}
		}
	}

	def compare(that : CrawlerTask) = {
		that.depth - this.depth
	}

}