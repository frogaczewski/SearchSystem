package pl.gda.pg.eti.sab.searchengine.client.util;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;

/**
 * @author Paweł Ogrodowczyk
 */
public class ResultHtmlRenderer implements IHtmlRenderer<JSONObject> {
	
	public String render(JSONObject object) {
		String result = "</br><p><a href=\"" + object.get("url").isString().stringValue() + "\">"
			+ object.get("title").isString().stringValue() + "</a></p>"
			+ "<p>" + object.get("description").isString().stringValue() + "</p>"
			+ "<p><i>" + object.get("url").isString().stringValue() + "</i></p></br>";
		return result;
	}
}
