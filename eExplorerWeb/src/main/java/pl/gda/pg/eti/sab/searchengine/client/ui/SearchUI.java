package pl.gda.pg.eti.sab.searchengine.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import pl.gda.pg.eti.sab.searchengine.client.ui.widgets.ResultTable;

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
	@UiField protected ResultTable resultTable;

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

	public void displayResults(JsArray<JavaScriptObject> results) {
		resultTable.clear();

		if (results.length() == 0) {
			Window.alert("Nie znaleziono wyników dla zapytania: " + searchQuery.getText());
		}
		else {
			rootPanel.getElement().getStyle().setTop(0, Unit.PX);
			for (int i = 0; i < results.length(); i++) {
				JSONObject result = new JSONObject(results.get(i));
				resultTable.addRow(result);
			}
		}
	}

	@UiHandler("searchButton")
	void searchButtonClick(ClickEvent event) {
		getResults();
	}

	@UiHandler("searchQuery")
	void searchQueryEnterPressed(KeyPressEvent event) {
		if (event.getCharCode() == KeyCodes.KEY_ENTER) {
			getResults();
        }
	}

	private void getResults() {
		String requestUrl = "http://127.0.0.1:8888/searchengine/proxied/search?query=" + searchQuery.getText();
		RequestBuilder builder = new RequestBuilder(
				RequestBuilder.GET, URL.encode(requestUrl));
		builder.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		builder.setCallback(new RequestCallback() {
				public void onResponseReceived(Request request, Response response) {
					JsArray<JavaScriptObject> results = (JsArray<JavaScriptObject>)((JSONArray) JSONParser.parseStrict(response.getText())).getJavaScriptObject();
					displayResults(results);
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