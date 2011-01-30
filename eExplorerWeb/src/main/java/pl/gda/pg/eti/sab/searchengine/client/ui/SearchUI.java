package pl.gda.pg.eti.sab.searchengine.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import pl.gda.pg.eti.sab.searchengine.client.SearchResult;

import java.util.List;

/**
 * @author Paweł Ogrodowczyk
 */
public class SearchUI extends Composite {
	interface SearchUiBinder extends UiBinder<VerticalPanel, SearchUI> {
	}

	private static SearchUiBinder uiBinder = GWT.create(SearchUiBinder.class);

	@UiField protected TextBox searchQuery;
	@UiField protected Button searchButton;
	@UiField protected MenuItem techButton;
	@UiField protected MenuItem authorsButton;
	@UiField protected FlexTable resultTable;

	private VerticalPanel rootPanel;

	public SearchUI() {
		rootPanel = uiBinder.createAndBindUi(this);
		initWidget(rootPanel);

		authorsButton.setCommand(new Command() {
			public void execute() {
				Window.alert("//TODO: DialogBox z autorami...");
			}
		});

		techButton.setCommand(new Command() {
			public void execute() {
				Window.alert("//TODO: DialogBox z wykorzystanymi technologiami...");
			}
		});
	}

	public void setResults(List<SearchResult> results) {
		rootPanel.getElement().getStyle().setTop(0, Unit.PX);
		resultTable.removeAllRows();
		for (SearchResult r : results) {
			resultTable.setHTML(resultTable.getRowCount(), 0,
					"<a href=\"" + r.getUrl() + "\">" + r.getTitle() + "</a>"
					+ "<p>" + r.getExcerpt() + "</p>"
					+ "PageRank: " + r.getPageRank() + "</br>"
					+ r.getUrl() + "</br></br>");
		}
	}

	private int jsonRequestId = 0;

	@UiHandler("searchButton")
	void searchButtonClick(ClickEvent event) {
		String requestUrl = "http://127.0.0.1:8888/searchengine/proxied/search?query=" + searchQuery.getText();
		RequestBuilder builder = new RequestBuilder(
				RequestBuilder.GET, URL.encode(requestUrl));
		builder.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		builder.setCallback(new RequestCallback() {
				public void onResponseReceived(Request request, Response response) {
					JSONArray results = (JSONArray) JSONParser.parseStrict(response.getText());
					Window.alert("results size is " + results.size());
					for (int i = 0; i < results.size(); i++) {
						JSONValue result = results.get(i);
						Window.alert("result " + result.toString());
					}
					Window.alert("result is " + response.getText());
				}

				public void onError(Request request, Throwable throwable) {
					Window.alert("There was an error " + throwable.getMessage());
				}
			});
		try {
			builder.send();
		} catch (RequestException e) {
			Window.alert(e.getMessage());
		}
	}
}