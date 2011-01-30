package pl.gda.pg.eti.sab

import crawler.{Crawler, PageFactory}
import actors.Actor
import indexer.Indexer
import pagerank.PageRanker
import server.Server
import util.{Logging, CloseMessage, CrawlerFinishedMessage}

/**
 * 
 * @author Filip Rogaczewski
 */
object Explorer extends Actor with Logging {

	override def start() : Actor = {
		log.info("Starting arachne");
		Server.start
		Crawler.start
		super.start
	}

	def main(args : Array[String]) : Unit = {
		Explorer.start
	}

	def act() = {
		loop {
			react {
				case CrawlerFinishedMessage => crawlerFinished
			}
		}
	}

	private def crawlerFinished() = {
		Crawler ! CloseMessage
		PageRanker.rank(PageFactory.pageMap)
		Indexer.index
	}

}