package pl.gda.pg.eti.sab.search

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.queryParser.MultiFieldQueryParser
import org.apache.lucene.util.{Version => LuceneVersion}
import pl.gda.pg.eti.sab.util.CrawlerPropertiesReader
import org.apache.lucene.search.{SortField, Sort, Query}

/**
 * 
 * @author Filip Rogaczewski
 */
object searchEngineUtil {

	lazy val indexedFields = Array("keywords", "description", "title", "body")

	lazy val sort =
		new Sort(Array(SortField.FIELD_SCORE, new SortField("title", SortField.SCORE), new SortField("keywords", SortField.SCORE)):_*)

	def query(term : String) : Query = {
		val parser = new MultiFieldQueryParser(LuceneVersion.LUCENE_30, indexedFields, new StandardAnalyzer(LuceneVersion.LUCENE_30))
		parser.parse(fuzzyQuery(term))
	}

	private def fuzzyQuery(term : String) = {
		term.concat(CrawlerPropertiesReader.searchFuzzyFactor)
	}
}