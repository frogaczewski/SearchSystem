package pl.gda.pg.eti.sab

import actors.Actor
import crawler.{RobotsProtocolAnalyzer, Crawler, PageFactory}
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
		RobotsProtocolAnalyzer.start
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