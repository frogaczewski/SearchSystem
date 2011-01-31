package pl.gda.pg.eti.sab.search

/**
* @author Paweł Ogrodowczyk
*/
trait SearchEngine {

	def search(query : String) : java.util.List[SearchResult]
}