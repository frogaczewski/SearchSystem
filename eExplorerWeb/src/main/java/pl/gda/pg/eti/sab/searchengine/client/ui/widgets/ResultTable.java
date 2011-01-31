package pl.gda.pg.eti.sab.searchengine.client.ui.widgets;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import pl.gda.pg.eti.sab.searchengine.client.util.ResultHtmlRenderer;

import java.util.List;

/**
 * @author Paweł Ogrodowczyk
 */
public class ResultTable extends Composite{
	interface ResultTableUiBinder extends UiBinder<VerticalPanel, ResultTable> {
	}

	private static ResultTableUiBinder ourUiBinder = GWT.create(ResultTableUiBinder.class);

	private ListDataProvider<JSONObject> dataProvider;

	@UiField(provided = true) protected CellTable<JSONObject> resultTable;
	@UiField(provided = true) protected SimplePager resultPager;

	@UiConstructor
	public ResultTable(int pageSize) {
		dataProvider = new ListDataProvider<JSONObject>();

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

		dataProvider.addDataDisplay(resultTable);
	}

	public void addRow(JSONObject row) {
		List<JSONObject> rows = dataProvider.getList();
		rows.add(row);
		resultPager.setVisible(true);
	}

	public void clear() {
		List<JSONObject> rows = dataProvider.getList();
		rows.clear();
		resultPager.setVisible(false);
	}
}