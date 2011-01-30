package pl.gda.pg.eti.sab.util

import org.apache.log4j.Logger

/**
 * Logging facade for scala source code.
 *
 * @author Filip Rogaczewski
 */
trait Logging {
	lazy val log = Logger.getLogger(this.getClass.getCanonicalName)
}