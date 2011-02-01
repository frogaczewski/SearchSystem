package pl.gda.pg.eti.sab.searchengine.client.ui.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Paweł Ogrodowczyk
 */
public class TechnologyInfo extends Composite{
	interface TechnologyInfoUiBinder extends UiBinder<HTMLPanel, TechnologyInfo> {
	}

	private static TechnologyInfoUiBinder ourUiBinder = GWT.create(TechnologyInfoUiBinder.class);

	public TechnologyInfo() {
		HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
		initWidget(rootElement);
	}
}