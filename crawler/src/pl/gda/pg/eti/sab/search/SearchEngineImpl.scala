package pl.gda.pg.eti.sab.search

import pl.gda.pg.eti.sab.util.CrawlerPropertiesReader
import org.apache.lucene.store.FSDirectory
import java.io.File
import org.apache.lucene.search.{ScoreDoc, IndexSearcher}
import org.apache.lucene.util.{Version => LuceneVersion}

/**
 * 
 * @author Filip Rogaczewski
 */
class SearchEngineImpl extends SearchEngine {

	def search(term : String) : java.util.List[Object] = {
		val searcher = new IndexSearcher(FSDirectory.open(new File(CrawlerPropertiesReader.indexDirectory)))
		val query = searchEngineUtil.query(term)
		val docs = searcher.search(query, null, 10, searchEngineUtil.sort)
		println("\n\nSzukanie " + term)
		docs.scoreDocs.foreach((doc : ScoreDoc) => {
			val document = searcher.doc(doc.doc)
			println(document.get("title") + document.get("description") + document.get("url"))
		})
		null
	}

}