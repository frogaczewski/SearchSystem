package pl.gda.pg.eti.sab.searchengine.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import pl.gda.pg.eti.sab.searchengine.client.ui.SearchUI;

/**
 * @author Paweł Ogrodowczyk
 */
public class SearchEngine implements EntryPoint {

	public void onModuleLoad() {
		SearchUI searchUI = new SearchUI();
		RootPanel.get().add(searchUI);
	}
}
