package pl.gda.pg.eti.sab.crawler

import collection.mutable
import net.htmlparser.jericho.Source
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.GetMethod
import org.apache.commons.httpclient.params.{HttpClientParams, HttpConnectionParams}
import pl.gda.pg.eti.sab.util.{CrawlerPropertiesReader, htmlContentUtil}

/**
 * 
 * @author Filip Rogaczewski
 */
object PageFactory {

	val pageMap = mutable.Map.empty[String, PageEntity]

	def apply(url : String, depth : Int) : Option[PageEntity] = {
		pageMap.get(url) match {
			case Some(page) => {
				None
			}
			case None => {
				val page = new PageEntity(url, depth)
				pageMap += ((url, page))
				Some(page)
			}
		}
	}
}


class PageEntity(val url : String, val depth : Int) {

	lazy val source : Source = {
		val params = new HttpClientParams
		params.setConnectionManagerTimeout(CrawlerPropertiesReader.connectionTimeout)
		val client = new HttpClient(params)
		val get = new GetMethod(url)
		client.executeMethod(get)
		new Source(get.getResponseBodyAsStream)
	}

	val title = htmlContentUtil.pageTitle(source)
	val keywords = htmlContentUtil.metaKeywords(source)
	val description = htmlContentUtil.metaDescription(source)
	val links : mutable.Set[String] = htmlContentUtil.links(source)
	val body = htmlContentUtil.body(source)

}