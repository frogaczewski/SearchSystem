package pl.gda.pg.eti.sab.searchengine.client.ui.widgets;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
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
import com.google.gwt.view.client.RangeChangeEvent;
import pl.gda.pg.eti.sab.searchengine.client.util.Settings;
import pl.gda.pg.eti.sab.searchengine.client.util.data.ILazyFetcher;
import pl.gda.pg.eti.sab.searchengine.client.util.html.IHtmlRenderer;

/**
 * @author Paweł Ogrodowczyk
 *
 * @param <Row> Row type.
 * @param <RowRenderer> Row renderer type.
 */
public class ResultTable<Row, RowRenderer extends IHtmlRenderer> extends Composite{
	interface ResultTableUiBinder extends UiBinder<VerticalPanel, ResultTable> {
	}

	private static ResultTableUiBinder ourUiBinder = GWT.create(ResultTableUiBinder.class);

	@UiField(provided = true) protected CellTable<Row> resultTable;
	@UiField(provided = true) protected SimplePager resultPager;

	private ILazyFetcher<ListDataProvider<Row>> resultFetcher;
	private RowRenderer rowRenderer;

	@UiConstructor
	public ResultTable(int pageSize) {
		resultTable = new CellTable<Row>(pageSize);/*, new ProvidesKey<JSONObject>() {
			public Object getKey(JSONObject jsonObject) {
				return jsonObject.get("url");
			}
		});*/

		resultTable.setSelectionModel(new NoSelectionModel<Row>());
		initTableColumns();

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		resultPager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);
		resultPager.setPageSize(pageSize);
		resultPager.setDisplay(resultTable);

		VerticalPanel rootElement = ourUiBinder.createAndBindUi(this);
		initWidget(rootElement);
	}

	private void initTableColumns() {
		SafeHtmlCell cell = new SafeHtmlCell();
		Column<Row, SafeHtml> column = new Column<Row, SafeHtml>(cell) {

			@Override
			public SafeHtml getValue(Row row) {
				final Row item = row;
				return new SafeHtml() {
					public String asString() {
						return rowRenderer.render(item);
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

	public void setRowRenderer(RowRenderer rowRenderer) {
		this.rowRenderer = rowRenderer;
	}

	public void setResultFetcher(ILazyFetcher<ListDataProvider<Row>> resultFetcher) {
		this.resultFetcher = resultFetcher;
		resultFetcher.getDataProvider().addDataDisplay(resultTable);
	}

	public void setVisible(boolean visible) {
		resultTable.setVisible(visible);
		resultPager.setVisible(visible);
	}

	public void setPage(int index) {
		resultPager.setPage(index);
	}
}