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

	lazy val log = Logger.getLogger(this.getClass.getCanonicalName)

	def makeModel(pages : Iterator[PageEntity]) : mutable.Set[Document] = {
		log.info("Making documents model.")
		val docs = new mutable.HashSet[Document]
		pages.foreach(
			docs += buildDocument(_)
		)
		log.info("Document model ready.")
		docs
	}

	private def buildDocument(page : PageEntity) : Document = {
		val document = new Document
		document.add(new Field("keywords", page.keywords.getOrElse(""), Field.Store.NO, Field.Index.ANALYZED))
		document.add(new Field("description", page.description.getOrElse(""), Field.Store.YES, Field.Index.ANALYZED))
		document.add(new Field("url", page.url, Field.Store.YES, Field.Index.NO))
		document.setBoost(1.0f)
		document
	}
}