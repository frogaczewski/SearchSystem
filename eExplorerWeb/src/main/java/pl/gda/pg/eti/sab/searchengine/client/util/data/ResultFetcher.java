package pl.gda.pg.eti.sab.searchengine.client.util.data;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.ListDataProvider;

import java.util.List;

/**
 * @author Paweł Ogrodowczyk
 */
public class ResultFetcher implements ILazyFetcher<ListDataProvider<JSONObject>> {

	private ListDataProvider<JSONObject> listDataProvider;
	private IFetchCallback fetchCallback;
	private String query;
	private int from;

	public ResultFetcher() {
		listDataProvider = new ListDataProvider<JSONObject>();
		from = 0;
	}

	public void fetch(int count) {
		final long timeTaken = System.currentTimeMillis();
		String requestUrl = "http://127.0.0.1:8888/searchengine/proxied/search?query=" + query + "&from=" + from + "&to=" + (from + count);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(requestUrl));
		builder.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		builder.setCallback(new RequestCallback() {
				public void onResponseReceived(Request request, Response response) {
					Window.alert(response.getText());
					JSONArray receivedData = JSONParser.parseStrict(response.getText()).isArray();
					int resultCount = (int)receivedData.get(0).isNumber().doubleValue();
					JSONArray results = receivedData.get(1).isArray();

					fetchCallback.onFetchComplete(resultCount, System.currentTimeMillis() - timeTaken);

					List<JSONObject> rows = listDataProvider.getList();
					for (int i = 0; i < results.size(); i++) {
						rows.add(results.get(i).isObject());
					}

					from += results.size();
				}

				public void onError(Request request, Throwable throwable) {
					fetchCallback.onFetchFailure();
				}
			});
		try {
			Window.alert("Sending request: " + requestUrl);
			builder.send();
		} catch (RequestException e) {
			Window.alert(e.getMessage());
		}
	}

	public void setFetchCallback(IFetchCallback callback) {
		fetchCallback = callback;
	}

	public void flush() {
		List<JSONObject> rows = listDataProvider.getList();
		rows.clear();
	}

	public ListDataProvider<JSONObject> getDataProvider() {
		return listDataProvider;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
