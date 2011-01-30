package pl.gda.pg.eti.sab.searchengine.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import pl.gda.pg.eti.sab.searchengine.client.SearchResult;
import pl.gda.pg.eti.sab.searchengine.client.SearchService;

import java.util.List;

public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

	public List<SearchResult> search(String query) {

		return null;
	}
}