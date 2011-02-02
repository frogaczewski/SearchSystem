package pl.gda.pg.eti.sab.searchengine.client.util.html;

import com.google.gwt.json.client.JSONObject;

/**
 * @author Paweł Ogrodowczyk
 */
public class ResultHtmlRenderer implements IHtmlRenderer<JSONObject> {

	private static final String THUMBNAIL_SERVICE_URL = "http://img.freewebsitethumbnails.com/?u=";
	
	public String render(JSONObject object) {
		HtmlStringBuilder htmlBuilder = new HtmlStringBuilder();

		try {
			htmlBuilder
			.addTag("table").addAttribute("style", "width: 100%;")
				.addTag("tr")
					.addTag("td").addAttribute("style", "padding-right: 1em;")
						.addTag("p")
							.addTag("a").addAttribute("href", object.get("url").isString().stringValue())
								.addInner(object.get("title").isString().stringValue())
							.closeTag()
						.closeTag()
						.addTag("p")
							.addInner(object.get("description").isString().stringValue())
						.closeTag()
						.addTag("p")
							.addTag("i")
								.addInner(object.get("url").isString().stringValue())
							.closeTag()
						.closeTag()
					.closeTag()
					.addTag("td").addAttribute("align", "right")
						.addTag("img")
							.addAttribute("src", THUMBNAIL_SERVICE_URL + object.get("url").isString().stringValue())
							.addAttribute("alt", "Podgląd strony")
							.addAttribute("style", "border: 1px solid #BBB;")
						.closeTagImmediately()
					.closeTag()
				.closeTag()
			.closeTag();

			return htmlBuilder.getHtmlString();
		} catch (Exception e) {
			return "<b>" + e.getMessage() + "</b>";
		}
	}
}
