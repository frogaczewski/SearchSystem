package pl.gda.pg.eti.sab.searchengine.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import pl.gda.pg.eti.sab.searchengine.client.SearchResult;
import pl.gda.pg.eti.sab.searchengine.client.SearchService;

import java.util.ArrayList;
import java.util.List;

public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

	public List<SearchResult> search(String query) {
		//TODO: implement method
		List<SearchResult> list = new ArrayList<SearchResult>();
		list.add(new SearchResult("Google", "Wyciąg tekstu z Google'a", "http:\\\\www.google.com", 1));
		list.add(new SearchResult("Politechnika Gdańska", "Wyciąg tekstu z PG", "http:\\\\www.pg.gda.pl", 0.99));
		return list;
	}
}