package pl.gda.pg.eti.sab

import crawler.Crawler
import indexer.Indexer

/**
 * 
 * @author Filip Rogaczewski
 */
object Arachne {

	def main(args : Array[String]) : Unit = {
		Crawler.start
		Indexer.start
	}

}