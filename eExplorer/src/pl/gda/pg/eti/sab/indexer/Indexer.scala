package pl.gda.pg.eti.sab.indexer

import pl.gda.pg.eti.sab.crawler.PageFactory
import org.apache.log4j.Logger
import pl.gda.pg.eti.sab.util.{Logging, CrawlerPropertiesReader}

/**
 * 
 * @author Filip Rogaczewski
 */
object Indexer extends Logging {

	def index() : Unit = {
		log.info("Indexer started.")
		new HtmlIndexer(PageFactory.pageMap.valuesIterator, CrawlerPropertiesReader.indexDirectory).index
	}

}