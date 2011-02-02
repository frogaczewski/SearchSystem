package pl.gda.pg.eti.sab.pagerank

import collection.immutable
import collection.mutable
import pl.gda.pg.eti.sab.crawler.PageEntity
import org.apache.log4j.Logger
import pl.gda.pg.eti.sab.util.Logging

/**
 * Evaluates PageRank for choosen page. Algorithm derived from wikipedia:
 * http://en.wikipedia.org/wiki/PageRank
 *
 * @author Filip Rogaczewski
 */
class PageRankTask(val pages : immutable.Map[String, PageEntity], val page : PageEntity)
	extends Runnable with Logging {

	def run() : Unit = {
		log.info("Ranking page " + page.url)
		var ranking = (1 - PageRanker.dampingFactor)
		references.foreach((p : PageEntity) => {
			ranking += PageRanker.dampingFactor * (p.pageRank / p.links.size)
		})
		page.tempPageRank = ranking
		log.info("Ranking for page " + page.url + " is " + page.tempPageRank)
	}

	/**
	 * Returns set of pages that reference this page.
	 */
	private def references() = {
		var referenceLinks = new mutable.HashSet[PageEntity]
		pages.valuesIterator.foreach((p : PageEntity) => {
			if (p.references(page.url))
				referenceLinks += p
		})
		referenceLinks
	}

}