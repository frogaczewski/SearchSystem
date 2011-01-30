package pl.gda.pg.eti.sab.util

/**
 * 
 * @author Filip Rogaczewski
 */
object Optional {
	def unapply[T](a : T) = if (a == null)Some(None) else Some(Some(a))
}