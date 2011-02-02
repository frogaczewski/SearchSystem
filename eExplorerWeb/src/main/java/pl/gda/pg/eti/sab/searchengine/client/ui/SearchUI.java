package pl.gda.pg.eti.sab.searchengine.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import pl.gda.pg.eti.sab.searchengine.client.ui.widgets.AuthorsInfo;
import pl.gda.pg.eti.sab.searchengine.client.ui.widgets.InfoBox;
import pl.gda.pg.eti.sab.searchengine.client.ui.widgets.ResultTable;
import pl.gda.pg.eti.sab.searchengine.client.ui.widgets.TechnologyInfo;
import pl.gda.pg.eti.sab.searchengine.client.util.Settings;
import pl.gda.pg.eti.sab.searchengine.client.util.data.IFetchCallback;
import pl.gda.pg.eti.sab.searchengine.client.util.data.ILazyFetcher;
import pl.gda.pg.eti.sab.searchengine.client.util.data.ResultFetcher;
import pl.gda.pg.eti.sab.searchengine.client.util.html.ResultHtmlRenderer;

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
	@UiField protected ResultTable<JSONObject, ResultHtmlRenderer> resultTable;
	@UiField protected HorizontalPanel resultInfo;
	@UiField protected Label resultCount;

	private VerticalPanel rootPanel;
	private InfoBox authorsInfoBox;
	private InfoBox technologyInfoBox;

	private ILazyFetcher<ListDataProvider<JSONObject>> resultFetcher;

	public SearchUI() {
		rootPanel = uiBinder.createAndBindUi(this);
		initWidget(rootPanel);

		resultFetcher = new ResultFetcher();
		resultFetcher.setFetchCallback(new IFetchCallback() {
			public void onFetchComplete(int totalResults) {

				if (totalResults == 0) {
					Window.alert("Nie znaleziono wyników dla zapytania: " + searchQuery.getText());
					rootPanel.getElement().getStyle().setTop(50, Unit.PCT);
					resultInfo.setVisible(false);
					resultTable.setVisible(false);
				} else {
					rootPanel.getElement().getStyle().setTop(0, Unit.PX);
					resultInfo.setVisible(true);
					resultCount.setText(String.valueOf(totalResults));
					resultTable.setVisible(true);
				}
			}

			public void onFetchFailure() {
				Window.alert("Failed to get results.");
			}
		});

		resultTable.setRowRenderer(new ResultHtmlRenderer());
		resultTable.setResultFetcher(resultFetcher);
		resultTable.setVisible(false);

		authorsInfoBox = new InfoBox("Autorzy", new AuthorsInfo());
		technologyInfoBox = new InfoBox("Wykorzystane technologie", new TechnologyInfo());

		resultInfo.setVisible(false);

		authorsButton.setCommand(new Command() {
			public void execute() {
				authorsInfoBox.display();
			}
		});

		techButton.setCommand(new Command() {
			public void execute() {
				technologyInfoBox.display();
			}
		});
	}

	@UiHandler("searchButton")
	public void searchButtonClick(ClickEvent event) {
		search();
	}

	@UiHandler("searchQuery")
	public void searchQueryEnterPressed(KeyPressEvent event) {
		int charCode = event.getUnicodeCharCode();

		//firefox workaround
		if (charCode == 0) {
			int keyCode = event.getNativeEvent().getKeyCode();

			if (keyCode == KeyCodes.KEY_ENTER) {
				search();
			}
		} else if (charCode == 13) {
			search();
        }
	}

	private void search() {
		resultFetcher.flush();
		resultFetcher.setQuery(searchQuery.getText());
		resultFetcher.fetch(Settings.PAGE_FETCH_COUNT);

		resultTable.setPage(0);
	}
}