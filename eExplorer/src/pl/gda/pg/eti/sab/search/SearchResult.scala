package pl.gda.pg.eti.sab.search

import reflect.BeanProperty
import org.apache.lucene.document.Document


/**
 * 
 * @author Filip Rogaczewski
 */
class SearchResult {

	@BeanProperty
	var title : String = _

	@BeanProperty
	var url : String = _

	@BeanProperty
	var description : String = _

}

object searchResultFactory {

	def apply(doc : Document) : SearchResult = {
		val result = new SearchResult
		result.title = doc.get("title")
		result.url = doc.get("url")
		result.description = doc.get("description")
		result
	}

}
