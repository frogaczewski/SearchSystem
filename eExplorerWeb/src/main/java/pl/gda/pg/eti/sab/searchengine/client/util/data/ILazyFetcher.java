package pl.gda.pg.eti.sab.searchengine.client.util.data;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.view.client.AbstractDataProvider;

/**
 * @author Paweł Ogrodowczyk
 *
 * @param <T> Data provider type.
 */
public interface ILazyFetcher<T extends AbstractDataProvider> {

	void fetch(int count);

	void setFetchCallback(IFetchCallback callback);

	void flush();

	T getDataProvider();

	void setQuery(String query);
}
