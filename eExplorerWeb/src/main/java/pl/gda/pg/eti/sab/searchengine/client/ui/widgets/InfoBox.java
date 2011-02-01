package pl.gda.pg.eti.sab.searchengine.client.ui.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

/**
 * @author Paweł Ogrodowczyk
 */
public class InfoBox extends DialogBox {

	public InfoBox(String caption, Composite info) {
		super(true, true);
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setHTML("<b>" + caption + "</b>");
		
		VerticalPanel rootPanel = new VerticalPanel();
		rootPanel.add(info);
		rootPanel.setCellHorizontalAlignment(info, HasHorizontalAlignment.ALIGN_LEFT);
		Button okButton = new Button("OK");
		okButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		rootPanel.add(okButton);
		rootPanel.setCellHorizontalAlignment(okButton, HasHorizontalAlignment.ALIGN_CENTER);

		setWidget(rootPanel);
	}

	public void display() {
		center();
		show();
	}
}
