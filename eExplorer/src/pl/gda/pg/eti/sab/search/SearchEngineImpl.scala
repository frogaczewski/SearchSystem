package pl.gda.pg.eti.sab.search

import org.apache.lucene.store.FSDirectory
import java.io.File
import org.apache.lucene.util.{Version => LuceneVersion}
import java.util.ArrayList
import pl.gda.pg.eti.sab.util.{Logging, CrawlerPropertiesReader}
import org.apache.lucene.search.{TopDocs, ScoreDoc, IndexSearcher}

/**
 * 
 * @author Filip Rogaczewski
 */
class SearchEngineImpl extends Logging {

	val searcher = new IndexSearcher(FSDirectory.open(new File(CrawlerPropertiesReader.indexDirectory)))

	/**
	 * Executes search on lucene directory.
	 * Document is loaded within a loop, so we can always can create a query
	 * for bigger set without any performance loss. 
	 */
	def search(term : String, from : Int, to : Int) : (java.util.List[SearchResult], Int) = {
		val results = new ArrayList[SearchResult]
		val query = searchEngineUtil.query(term)
		val queryStartTime = System.currentTimeMillis
		val docs : TopDocs = searcher.search(query, null, to, searchEngineUtil.sort)
		for (i <- from until to) {
			val document = searcher.doc(docs.scoreDocs(i).doc)
			results.add(searchResultFactory(document))
		}
		val queryTime = System.currentTimeMillis - queryStartTime
		log.info("Query for " + term + " took " +  queryTime)		
		(results, docs.totalHits)
	}

}