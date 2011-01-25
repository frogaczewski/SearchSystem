package pl.gda.pg.eti.sab.crawler

import org.apache.log4j.Logger

/**
 * 
 * @author Filip Rogaczewski
 */
class CrawlerTask(val url : String, val depth : Int) extends Runnable with Ordered[CrawlerTask] {

	lazy val log = Logger.getLogger(this.getClass.getCanonicalName)

	def run() : Unit = {
		try {
			log.info("Crawling " + url + " depth " + depth)
			val page = PageFactory(url, depth)
			page match {
				case Some(value) => Crawler ! value
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