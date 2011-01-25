package pl.gda.pg.eti.sab.crawler

import java.util.concurrent.Executors
import collection.mutable
import actors.Actor
import pl.gda.pg.eti.sab.util.CrawlerPropertiesReader
import pl.gda.pg.eti.sab.indexer.{StartIndexing, Indexer}
import org.apache.log4j.Logger

/**
 * 
 * @author Filip Rogaczewski
 */
object Crawler extends Actor {

	lazy val log = Logger.getLogger(this.getClass.getCanonicalName)

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

	override def start() : Actor = {
		crawlTasks += new CrawlerTask(CrawlerPropertiesReader.crawlerStartPage, 1)
		threadPool.execute(tasksExecutor)
		super.start
	}

	object tasksExecutor extends Runnable() {
		var timeout = false

		def run() : Unit = {
			while (!timeout) {
				execute
			}
			log.info("Crawler finished")
			Indexer ! StartIndexing
//			Crawler.exit
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

	def act() {
		loop {
			react {
				case msg => handleMessage(msg)
			}
		}
	}

	private def handleMessage(msg : Any) : Unit = {
		msg match {
			case page : PageEntity => createNewCrawlerTasks(page)
		}
	}

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

}