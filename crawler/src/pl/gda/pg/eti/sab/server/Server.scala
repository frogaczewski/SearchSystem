package pl.gda.pg.eti.sab.server

import org.mortbay.jetty.{Server => JettyServer}
import org.mortbay.jetty.servlet.{ServletHolder, Context}
import com.sun.jersey.spi.container.servlet.ServletContainer
import pl.gda.pg.eti.sab.util.CrawlerPropertiesReader

/**
 * 
 * @author Filip Rogaczewski
 */
object Server {

	val servetHolder = {
		val sh = new ServletHolder(classOf[ServletContainer])
		sh.setInitParameter("com.sun.jersey.config.property.packages", "pl.gda.pg.eti.sab.server")
		sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig")
		sh
	}

	val context = {
		val ctx = new Context(server, "/", Context.SESSIONS)
		ctx.addServlet(servetHolder, "/*")
		ctx
	}

	val server = {
		val srv = new JettyServer(CrawlerPropertiesReader.serverPort)
		srv
	}


	def start() = {
		server.setHandler(context)
		server.start
	}

}