package pl.gda.pg.eti.sab.server

import pl.gda.pg.eti.sab.search.{SearchResult, SearchEngineImpl}

/**
 * 
 * @author Filip Rogaczewski
 */
object SearcherFacade {

	val searchEngine = new SearchEngineImpl

	def search(query : String, from : Int = 0, to : Int = 10) : (java.util.List[SearchResult], Int) = {
		searchEngine.search(query, from, to)
	}

}