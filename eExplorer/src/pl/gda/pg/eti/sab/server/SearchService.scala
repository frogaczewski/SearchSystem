package pl.gda.pg.eti.sab.server

import pl.gda.pg.eti.sab.search.SearchResult
import javax.ws.rs.{Produces, QueryParam, GET, Path}
import com.google.gson.Gson
/**
 * 
 * @author Filip Rogaczewski
 */
@Path("/SearchService")
class SearchService {

	@GET
	@Path("/search")
	@Produces(Array("application/json"))
	def search(@QueryParam("query") query : String,
	           @QueryParam("from") from : Int,
		       @QueryParam("to") to : Int) : String = {
		val results = SearcherFacade.search(query)
		new Gson().toJson(new ServerResult(results._1, results._2))
	}
	
}

class ServerResult(val searchResult : java.util.List[SearchResult], val totalHits : Int)