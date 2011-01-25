package pl.gda.pg.eti.sab.indexer

import org.apache.lucene.document.Document
import collection.mutable
import org.apache.lucene.store.FSDirectory
import java.io.File
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index.IndexWriter
import org.apache.log4j.Logger
import org.apache.lucene.util.{Version => LuceneVersion}

/**
 * 
 * @author Filip Rogaczewski
 */
class HtmlIndexer(val docs : mutable.Set[Document], val directory : String) {

	lazy val log = Logger.getLogger(this.getClass.getCanonicalName)

	private val dir = FSDirectory.open(new File(directory))
	private val writer = new IndexWriter(dir, new StandardAnalyzer(LuceneVersion.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED)

	def index() : Unit = {
		log.info("Indexing " + docs.size + " documents")
		docs.foreach(writer.addDocument(_))
		writer.optimize
		writer.close
		log.info("All document indexed")
	}
}