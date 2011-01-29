package pl.gda.pg.eti.sab.searchengine.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by IntelliJ IDEA.
 * User: pawelo
 * Date: 25.01.11
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
public class SearchResult implements IsSerializable{

	private String title;
	private String excerpt;
	private String url;
	private double pageRank;

	public SearchResult() {
	}

	public SearchResult(String title, String excerpt, String url, double pageRank) {
		this.title = title;
		this.excerpt = excerpt;
		this.url = url;
		this.pageRank = pageRank;
	}

	@Override
	public String toString() {
		return "SearchResult{" +
				"title='" + title + '\'' +
				", excerpt='" + excerpt + '\'' +
				", url='" + url + '\'' +
				", pageRank=" + pageRank +
				'}';
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getPageRank() {
		return pageRank;
	}

	public void setPageRank(double pageRank) {
		this.pageRank = pageRank;
	}
}
