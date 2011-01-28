package pl.gda.pg.eti.sab.searchengine.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import pl.gda.pg.eti.sab.searchengine.client.SearchResult;
import pl.gda.pg.eti.sab.searchengine.client.SearchService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pawelo
 * Date: 25.01.11
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
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

	@UiHandler("searchButton")
	void searchButtonClick(ClickEvent event) {
		SearchService.App.getInstance().search(searchQuery.getText(), new SearchCallback(this));
	}


	private static class SearchCallback implements AsyncCallback<List<SearchResult>> {

		private SearchUI searchUI;

		public SearchCallback(SearchUI searchUI) {
			this.searchUI = searchUI;
		}

		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
		}

		public void onSuccess(List<SearchResult> result) {
			searchUI.setResults(result);
		}
	}
}