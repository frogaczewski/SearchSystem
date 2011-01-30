package pl.gda.pg.eti.sab.searchengine.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface SearchServiceAsync {

	void search(String query, AsyncCallback<List<SearchResult>> async);
}
