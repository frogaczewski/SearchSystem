package pl.gda.pg.eti.sab.searchengine.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("SearchService")
public interface SearchService extends RemoteService {

	List<SearchResult> search(String query);

	/**
	 * Utility/Convenience class.
	 * Use SearchService.App.getInstance() to access static instance of SearchServiceAsync
	 */
	public static class App {
		private static SearchServiceAsync ourInstance = GWT.create(SearchService.class);

		public static synchronized SearchServiceAsync getInstance() {
			return ourInstance;
		}
	}
}
