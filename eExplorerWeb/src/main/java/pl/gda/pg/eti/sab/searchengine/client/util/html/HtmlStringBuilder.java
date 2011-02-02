package pl.gda.pg.eti.sab.searchengine.client.util.html;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @author Paweł Ogrodowczyk
 */
public class HtmlStringBuilder {

	private StringBuilder sb;
	private Stack<String> openTags;
	private boolean addedInner;

	public HtmlStringBuilder() {
		reset();
	}

	public void reset() {
		sb = new StringBuilder();
		openTags = new Stack<String>();

		addedInner = false;
	}

	public HtmlStringBuilder addTag(String name) {
		if (!addedInner && openTags.size() > 0) {
			sb.append('>');
		}

		sb.append('<');
		sb.append(name);

		openTags.push(name);

		addedInner = false;

		return this;
	}

	public HtmlStringBuilder addAttribute(String name, String value) {
		sb.append(' ');
		sb.append(name);
		sb.append("=\"");
		sb.append(value);
		sb.append('"');

		addedInner = false;

		return this;
	}

	public HtmlStringBuilder addInner(String inner) {
		if (!addedInner && openTags.size() > 0) {
			sb.append('>');
		}

		sb.append(inner);

		addedInner = true;

		return this;
	}

	public HtmlStringBuilder closeTagImmediately() throws NoOpenTagsException {
		sb.append(" />");
		try{
			openTags.pop();
		} catch (EmptyStackException e) {
			throw new NoOpenTagsException();
		}

		return this;
	}

	public HtmlStringBuilder closeTag() throws NoOpenTagsException {
		sb.append("</");
		try {
			sb.append(openTags.pop());
		} catch (EmptyStackException e) {
			throw new NoOpenTagsException();
		}
		sb.append('>');

		return this;
	}

	public String getHtmlString() throws UnclosedTagsException {
		if (openTags.size() > 0) {
			throw new UnclosedTagsException();
		}

		return sb.toString();
	}


	public class UnclosedTagsException extends Exception {

		public UnclosedTagsException() {
			super("Some tags remain unclosed!");
		}
	}

	public class NoOpenTagsException extends Exception {

		public NoOpenTagsException() {
			super("There are no more tags to close!");
		}
	}
}
