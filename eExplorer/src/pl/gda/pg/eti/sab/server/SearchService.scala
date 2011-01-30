package pl.gda.pg.eti.sab.server

import scala.collection.JavaConversions._
import pl.gda.pg.eti.sab.search.SearchResult
import scala.util.parsing.json._
import javax.ws.rs.{Produces, QueryParam, GET, Path}
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 
 * @author Filip Rogaczewski
 */
@Path("/SearchService")
class SearchService {

	@GET
	@Path("/search")
	@Produces(Array("application/json"))
	def search(@QueryParam("query") query : String) : String = {
		val jsonType = new TypeToken[java.util.List[SearchResult]](){}.getType
		new Gson().toJson(SearcherFacade.search(query), jsonType)
	}
}