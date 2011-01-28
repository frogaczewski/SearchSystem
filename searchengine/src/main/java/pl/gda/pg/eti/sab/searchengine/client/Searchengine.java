package pl.gda.pg.eti.sab.searchengine.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import pl.gda.pg.eti.sab.searchengine.client.ui.SearchUI;

/**
 * Created by IntelliJ IDEA.
 * User: pawelo
 * Date: 28.01.11
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
public class SearchEngine implements EntryPoint {

	public void onModuleLoad() {
		SearchUI searchUI = new SearchUI();
		RootPanel.get().add(searchUI);
	}
}
