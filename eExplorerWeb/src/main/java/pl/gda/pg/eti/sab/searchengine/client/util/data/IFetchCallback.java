package pl.gda.pg.eti.sab.searchengine.client.util.data;

/**
 * @author Paweł Ogrodowczyk
 */
public interface IFetchCallback {

	void onFetchComplete(int totalResults);

	void onFetchFailure();
}
