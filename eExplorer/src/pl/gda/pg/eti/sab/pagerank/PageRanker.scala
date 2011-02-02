package pl.gda.pg.eti.sab.pagerank

import collection.mutable
import pl.gda.pg.eti.sab.crawler.PageEntity
import java.util.concurrent.{TimeUnit, Executors}
import pl.gda.pg.eti.sab.util.{Logging, CrawlerPropertiesReader}

/**
 * Object managing execution of PageRank algorithm.
 *
 * @author Filip Rogaczewski
 */
object PageRanker extends Logging {

	val dampingFactor = CrawlerPropertiesReader.dampingFactor

	val pageRankerThreadPool = Executors.newFixedThreadPool(CrawlerPropertiesReader.rankerThreadPoolSize)

	def rank(pages : mutable.Map[String, PageEntity]) = {
		log.info("PageRanker started")
		for (i <- 0 until CrawlerPropertiesReader.rankerIterations)
			iteration(pages)
	}

	private def iteration(pages : mutable.Map[String, PageEntity]) = {
		val immutablePagesMap = pages.toMap
		immutablePagesMap.keySet.foreach((url : String) => {
			pageRankerThreadPool.execute(new PageRankTask(immutablePagesMap, immutablePagesMap.get(url).get))
		})
		pageRankerThreadPool.awaitTermination(60, TimeUnit.SECONDS)
		immutablePagesMap.keySet.foreach((url : String) => {
			immutablePagesMap.get(url).get.adjustPageRank
			log.info("Page rank of " + url + " set to " + immutablePagesMap.get(url).get.pageRank)
		})
	}

}