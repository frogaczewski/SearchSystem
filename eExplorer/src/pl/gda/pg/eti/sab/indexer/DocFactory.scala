package pl.gda.pg.eti.sab.indexer

import pl.gda.pg.eti.sab.crawler.PageEntity
import collection.mutable
import org.apache.lucene.document.{Field, Document}
import org.apache.log4j.Logger

/**
 * 
 * @author Filip Rogaczewski
 */
object docFactory {

	def buildDocument(page : PageEntity) : Document = {
		val document = new Document
		document.add(new Field("keywords", page.keywords.getOrElse(""), Field.Store.NO, Field.Index.ANALYZED))
		document.add(new Field("description", page.description.getOrElse(""), Field.Store.YES, Field.Index.ANALYZED))
		document.add(new Field("url", page.url, Field.Store.YES, Field.Index.NO))
		document.add(new Field("title", page.title.getOrElse(""), Field.Store.YES, Field.Index.ANALYZED))
		document.add(new Field("body", page.body.getOrElse(""), Field.Store.NO, Field.Index.ANALYZED))
		document.setBoost(page.pageRank.floatValue)
		document
	}
}