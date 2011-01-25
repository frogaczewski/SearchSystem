package pl.gda.pg.eti.sab.indexer

import actors.Actor
import pl.gda.pg.eti.sab.crawler.PageFactory
import pl.gda.pg.eti.sab.util.CrawlerPropertiesReader
import org.apache.log4j.Logger

/**
 * 
 * @author Filip Rogaczewski
 */
object Indexer extends Actor {

	lazy val log = Logger.getLogger(this.getClass.getCanonicalName)

	override def start() : Actor = {
		super.start
	}

	def act() = {
		loop {
			react {
				case StartIndexing => index
			}
		}
	}

	private def index() : Unit = {
		log.info("Indexer started.")
	    val docs = docFactory.makeModel(PageFactory.pageMap.valuesIterator)
		// TODO set boost from page rank
		new HtmlIndexer(docs, CrawlerPropertiesReader.indexDirectory).index
	}

}

case object StartIndexing