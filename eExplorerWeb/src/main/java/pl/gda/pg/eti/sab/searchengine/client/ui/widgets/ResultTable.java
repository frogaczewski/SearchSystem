package pl.gda.pg.eti.sab.searchengine.client.ui.widgets;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.RangeChangeEvent;
import pl.gda.pg.eti.sab.searchengine.client.util.Settings;
import pl.gda.pg.eti.sab.searchengine.client.util.data.ILazyFetcher;
import pl.gda.pg.eti.sab.searchengine.client.util.data.ResultFetcher;
import pl.gda.pg.eti.sab.searchengine.client.util.html.ResultHtmlRenderer;

import java.util.List;

/**
 * @author Paweł Ogrodowczyk
 */
public class ResultTable extends Composite{
	interface ResultTableUiBinder extends UiBinder<VerticalPanel, ResultTable> {
	}

	private static ResultTableUiBinder ourUiBinder = GWT.create(ResultTableUiBinder.class);

	@UiField(provided = true) protected CellTable<JSONObject> resultTable;
	@UiField(provided = true) protected SimplePager resultPager;

	private ILazyFetcher<ListDataProvider<JSONObject>> resultFetcher;

	@UiConstructor
	public ResultTable(int pageSize) {
		resultTable = new CellTable<JSONObject>(pageSize, new ProvidesKey<JSONObject>() {
			public Object getKey(JSONObject jsonObject) {
				return jsonObject.get("url");
			}
		});

		resultTable.setSelectionModel(new NoSelectionModel<JSONObject>());
		initTableColumns();

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		resultPager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);
		resultPager.setPageSize(pageSize);
		resultPager.setDisplay(resultTable);
		resultPager.setVisible(false);

		VerticalPanel rootElement = ourUiBinder.createAndBindUi(this);
		initWidget(rootElement);
	}

	private void initTableColumns() {
		SafeHtmlCell cell = new SafeHtmlCell();
		Column<JSONObject, SafeHtml> column = new Column<JSONObject, SafeHtml>(cell) {

			private final ResultHtmlRenderer htmlRenderer = new ResultHtmlRenderer();

			@Override
			public SafeHtml getValue(JSONObject jsonObject) {
				final JSONObject item = jsonObject;
				return new SafeHtml() {
					public String asString() {
						return htmlRenderer.render(item);
					}
				};

			}

		};

		resultTable.addColumn(column);
	}

	@UiHandler("resultTable")
	public void resultTableRangeChange(RangeChangeEvent event) {
		if (!resultPager.hasNextPage()) {
			resultFetcher.fetch(Settings.PAGE_FETCH_COUNT);
		}

		Window.scrollTo(0, 0);
	}

	public void setResultFetcher(ILazyFetcher<ListDataProvider<JSONObject>> resultFetcher) {
		this.resultFetcher = resultFetcher;
		resultFetcher.getDataProvider().addDataDisplay(resultTable);
	}
}