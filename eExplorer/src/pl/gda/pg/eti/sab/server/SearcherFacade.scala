package pl.gda.pg.eti.sab.server

import pl.gda.pg.eti.sab.search.{SearchResult, SearchEngineImpl}

/**
 * 
 * @author Filip Rogaczewski
 */
object SearcherFacade {

	val searchEngine = new SearchEngineImpl

	def search(query : String) : java.util.List[SearchResult] = {
		searchEngine.search(query)
	}

}