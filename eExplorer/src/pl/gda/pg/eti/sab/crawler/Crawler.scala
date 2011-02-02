package pl.gda.pg.eti.sab.crawler

import java.util.concurrent.Executors
import collection.mutable
import actors.Actor
import pl.gda.pg.eti.sab.Explorer
import pl.gda.pg.eti.sab.util.{Logging, CloseMessage, CrawlerFinishedMessage, CrawlerPropertiesReader}
import org.apache.commons.httpclient.methods.GetMethod
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.params.HttpClientParams
import java.io.{BufferedReader, InputStreamReader}

/**
 * Crawler object.
 *
 * @author Filip Rogaczewski
 */
object Crawler extends Actor with Logging {

	/**
	 * Crawler tasks to execute.
	 */
	val crawlTasks = new mutable.PriorityQueue[CrawlerTask]

	/**
	 * URLs of crawled pages.
	 */
	val crawled = new mutable.HashSet[String]

	/**
	 * Helper variable for synchronized execution.
	 */
	val lock : AnyRef = new Object()

	/**
	 * Maximum depth of crawler tree.
	 */
	val depthLimit = CrawlerPropertiesReader.crawlerDepth

	/**
	 * Executors thread pool.
	 */
	val threadPool = Executors.newFixedThreadPool(CrawlerPropertiesReader.crawlerThreadPoolSize)

	/**
	 * Timeout after which crawler terminated.
	 */
	val crawlerTimeout = CrawlerPropertiesReader.crawlerTimeout

	/**
	 * Robots protocol not indexed.
	 */
	val robotsDisallow = "Disallow: "

	/**
	 * Overriden start method. Starts crawler actor and initializes first crawler task for
	 * page defined in crawler properties.
	 */
	override def start() : Actor = {
		crawled ++= exclusions
		crawlTasks += new CrawlerTask(CrawlerPropertiesReader.crawlerStartPage, 1)
		threadPool.execute(tasksExecutor)
		super.start
	}

	/**
	 * Object that manages queue of pages to crawl.
	 */
	object tasksExecutor extends Runnable() {
		var timeout = false

		def run() : Unit = {
			while (!timeout) {
				execute
			}
			log.info("Crawler finished")
			Explorer ! CrawlerFinishedMessage
		}

		private def execute() = lock.synchronized {
			if (crawlTasks.isEmpty) {
				val start = System.currentTimeMillis
				lock.wait(crawlerTimeout)
				if (System.currentTimeMillis - start >= crawlerTimeout)
					timeout = true
			} else {
				val task = crawlTasks.dequeue
				threadPool.execute(task)
				crawled += task.url
			}
		}
	}

	/**
	 * Main actor method, crawler waits for messages about newly indexed page.
	 */
	def act() {
		loop {
			react {
				case CloseMessage => {
					log.info("Crawler exits")
					exit
				}
				case msg => handleMessage(msg)
			}
		}
	}

	/**
	 * Crawler incoming message handler.
	 */
	private def handleMessage(msg : Any) : Unit = {
		msg match {
			case page : PageEntity => createNewCrawlerTasks(page)
		}
	}

	/**
	 * Evaluates links of newly crawled page. If page references url which
	 * haven't been crawled this method creates new crawling tasks and pushes them
	 * to the priority queue with crawling tasks.
	 */
	private def createNewCrawlerTasks(page : PageEntity) = {
		page.links.foreach((link : String) => {
			 if (!crawled.contains(link) && page.depth < depthLimit) {
				 crawlTasks += new CrawlerTask(link, page.depth + 1)
			 }
		})
		unlockExecutor
	}

	private def unlockExecutor() : Unit = lock.synchronized {
		lock.notify
	}

	private def exclusions() : mutable.Set[String] = {
		val params = new HttpClientParams
		params.setConnectionManagerTimeout(CrawlerPropertiesReader.connectionTimeout)
		val client = new HttpClient(params)
		val get = new GetMethod(CrawlerPropertiesReader.crawlerRobotsProtocol)
		client.executeMethod(get)
		val stream = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream))
		var line = stream.readLine
		val result = new mutable.HashSet[String]
		while (line != null) {
			if (line.startsWith(robotsDisallow))
				result += CrawlerPropertiesReader.crawlerStartPage + line.substring(robotsDisallow.length)
			line = stream.readLine
		}
		result
	}

}