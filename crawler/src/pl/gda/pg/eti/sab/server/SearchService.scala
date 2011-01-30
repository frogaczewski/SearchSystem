package pl.gda.pg.eti.sab.server

import javax.ws.rs.{QueryParam, GET, Path}
import scala.collection.JavaConversions._
import pl.gda.pg.eti.sab.search.SearchResult

/**
 * 
 * @author Filip Rogaczewski
 */
@Path("/SearchService")
class SearchService {

	@GET
	@Path("/search")
	def search(@QueryParam("query") query : String) : String = {
		val searchResult = new StringBuilder
		SearcherFacade.search(query).foreach((result : SearchResult) => {
			searchResult.append(result.url)
		})
		searchResult.toString
	}
}