package pl.gda.pg.eti.sab.util

import net.htmlparser.jericho.{HTMLElementName, Element, Source}
import scala.collection.JavaConversions._
import collection.mutable
import util.control.Breaks

/**
 * 
 * @author Filip Rogaczewski
 */
object htmlContentUtil {

	def metaKeywords(source : Source) : Option[String] = {
		translate(source, getMetaValue(source, "keywords"))
	}

	def metaDescription(source : Source) : Option[String] = {
		translate(source, getMetaValue(source, "description"))
	}

	private def getMetaValue(source : Source, key : String) : Option[String] = {
		var result : Option[String] = None
		var found = false
		source.getAllElements(HTMLElementName.META).foreach((e : Element) => {
			val meta = e.getAttributeValue("name")
			if (!found && meta != null)
				result = getMetaContent(e, meta, key)
			else if (!found) {
				result = getMetaContent(e, e.getAttributeValue("property"), key, "og")
			}
			result match {
				case Some(x) => { found = true }
				case None => {}
			}
		})
		result
	}

	private def getMetaContent(e : Element, meta : String, key : String, prefix  : String = "") : Option[String] = {
		var result : Option[String] = None
		meta match {
			case Optional(Some(value)) => {
				if (value.equalsIgnoreCase(prefix + key))
					result = Some(e.getAttributeValue("content"))
			}
			case Optional(None) => {
			}
		}
		result
	}

	def pageTitle(source : Source) : Option[String] = {
		val title = Some(source.getFirstElement(HTMLElementName.TITLE).getContent.toString)
		translate(source, title)
	}

	def links(source : Source) : mutable.Set[String] = {
		var links = new mutable.HashSet[String]
		if (!nofollow(source)) {
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
		}
		links
	}

	def body(source : Source) : Option[String] = {
		val body = source.getFirstElement(HTMLElementName.BODY)
		Some(body.getTextExtractor.toString)
	}

	def index(source : Source) = !robots(source, "NOINDEX")

	def nofollow(source : Source) = robots(source, "NOFOLLOW")

	private def robots(source : Source, key : String) : Boolean = {
		val robots = getMetaValue(source, "robots")
		robots match {
			case Some(value) => {
				if (value.contains(key))
					true
				else {
					false
				}
			}
			case None => false
		}
	}

	/**
	 * TODO rename
	 *
	 */
	private def translate(source : Source, text : Option[String]) = {
		val toTranslate = text.getOrElse("")
		// html jericho does not support iso-8859-2. all ISO-8859-X are
		// recognized as ISO-8859-1. in eExplorer case we except ISO-8859-2 value
		// as the crawler is designed to index mostly polish content.
		if (source.getEncoding.equalsIgnoreCase("ISO-8859-1")) {
			val isoBytes = toTranslate.getBytes("ISO-8859-2")
			Some(new String(isoBytes, "UTF-8"))
		} else if (source.getEncoding.equalsIgnoreCase("ISO-8859-2")) {
			val isoBytes = toTranslate.getBytes("ISO-8859-2")
			Some(new String(isoBytes, "UTF-8"))			
		} else {
			Some(toTranslate)
		}
	}

}