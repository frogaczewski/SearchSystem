package pl.gda.pg.eti.sab.search

/**
* @author Paweł Ogrodowczyk
*/
trait SearchEngine {

	def search(query : String, from : Int, to : Int) : java.util.List[SearchResult]
}