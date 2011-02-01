package pl.gda.pg.eti.sab.searchengine.client.ui.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Paweł Ogrodowczyk
 */
public class AuthorsInfo extends Composite {
	interface AuthorsInfoUiBinder extends UiBinder<HTMLPanel, AuthorsInfo> {
	}

	private static AuthorsInfoUiBinder ourUiBinder = GWT.create(AuthorsInfoUiBinder.class);

	public AuthorsInfo() {
		HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
		initWidget(rootElement);
	}
}