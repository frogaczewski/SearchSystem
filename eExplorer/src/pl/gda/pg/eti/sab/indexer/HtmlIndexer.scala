package pl.gda.pg.eti.sab.indexer

import org.apache.lucene.store.FSDirectory
import java.io.File
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.util.{Version => LuceneVersion}
import pl.gda.pg.eti.sab.util.Logging
import pl.gda.pg.eti.sab.crawler.{RobotsProtocolAnalyzer, PageEntity}

/**
 * 
 * @author Filip Rogaczewski
 */
class HtmlIndexer(val pages : Iterator[PageEntity], val directory : String) extends Logging {

	private val dir = FSDirectory.open(new File(directory))
	private val writer = new IndexWriter(dir, new StandardAnalyzer(LuceneVersion.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED)

	def index() : Unit = {
		log.info("Indexing all documents")
		val indexStartTime = System.currentTimeMillis
		pages.foreach((page : PageEntity) => {
			if (page.index && RobotsProtocolAnalyzer.isNotExcluded(page.url)) {
				writer.addDocument(docFactory.buildDocument(page))
			} else {
				log.info("Page " + page.url + " is not indexed")
			}
		})
		writer.optimize
		writer.close
		log.info("All document indexed - time taken " + (System.currentTimeMillis - indexStartTime))
	}
}