package pl.gda.pg.eti.sab.util

import net.htmlparser.jericho.{HTMLElementName, Element, Source}
import scala.collection.JavaConversions._
import collection.mutable

/**
 * 
 * @author Filip Rogaczewski
 */
object htmlContentUtil {

	def metaKeywords(source : Source) : Option[String] = {
		getMetaValue(source, "keywords")
	}

	def metaDescription(source : Source) : Option[String] = {
		getMetaValue(source, "description")
	}

	private def getMetaValue(source : Source, key : String) : Option[String] = {
		var result : Option[String] = None
		source.getAllElements(HTMLElementName.META).foreach((e : Element) => {
			val meta = e.getAttributeValue("name")
			meta match {
				case Optional(Some(value)) => {
					if (value.equalsIgnoreCase(key))
						result = Some(e.getAttributeValue("content"))
				}
				case Optional(None) => {
				}
			}
		})
		result
	}

	def links(source : Source) : mutable.Set[String] = {
		var links = new mutable.HashSet[String]
		source.getAllElements(HTMLElementName.A).foreach((e : Element) => {
			val link = e.getAttributeValue("href")
			link match {
				case Optional(Some(value)) => {
					if (value.startsWith("http"))
						links += value
				}
				case Optional(None) => {}
			}
		})
		links
	}

}