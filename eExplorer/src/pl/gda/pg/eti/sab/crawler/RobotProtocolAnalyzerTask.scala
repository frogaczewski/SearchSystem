package pl.gda.pg.eti.sab.crawler

import org.apache.commons.httpclient.params.HttpClientParams
import java.io.{BufferedReader, InputStreamReader}
import org.apache.commons.httpclient.methods.GetMethod
import org.apache.commons.httpclient.HttpClient
import pl.gda.pg.eti.sab.util.{CrawlerPropertiesReader, Logging}
import collection.mutable

/**
 * 
 * @author Filip Rogaczewski
 */
class RobotsProtocolAnalyzerTask(val hostname : String) extends Runnable with Logging {

	def run() : Unit = {
		try {
			val excludedPages = exclusions
		} catch {
			case e : Exception => log.error(e.getMessage)
		}
		RobotsProtocolAnalyzer ! exclusions
	}

	@throws(classOf[Exception])
	private def exclusions() : mutable.Set[String] = {
		val params = new HttpClientParams
		params.setConnectionManagerTimeout(CrawlerPropertiesReader.connectionTimeout)
		val client = new HttpClient(params)
		val get = new GetMethod(hostname + CrawlerPropertiesReader.crawlerRobotsProtocol)
		client.executeMethod(get)
		val stream = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream))
		var line = stream.readLine
		val result = new mutable.HashSet[String]
		while (line != null) {
			if (line.startsWith(RobotsProtocolAnalyzer.robotsDisallow))
				result += hostname + line.substring(RobotsProtocolAnalyzer.robotsDisallow.length)
			line = stream.readLine
		}
		result
	}
}