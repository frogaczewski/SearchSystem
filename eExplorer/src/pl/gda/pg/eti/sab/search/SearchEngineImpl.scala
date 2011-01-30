package pl.gda.pg.eti.sab.search

import pl.gda.pg.eti.sab.util.CrawlerPropertiesReader
import org.apache.lucene.store.FSDirectory
import java.io.File
import org.apache.lucene.search.{ScoreDoc, IndexSearcher}
import org.apache.lucene.util.{Version => LuceneVersion}
import java.util.ArrayList

/**
 * 
 * @author Filip Rogaczewski
 */
class SearchEngineImpl extends SearchEngine {

	val searcher = new IndexSearcher(FSDirectory.open(new File(CrawlerPropertiesReader.indexDirectory)))

	def search(term : String) : java.util.List[SearchResult] = {
		val results = new ArrayList[SearchResult]
		val query = searchEngineUtil.query(term)
		val docs = searcher.search(query, null, 10, searchEngineUtil.sort)
		docs.scoreDocs.foreach((doc : ScoreDoc) => {
			val document = searcher.doc(doc.doc)
			results.add(searchResultFactory(document))
		})
		results
	}

}