package pl.gda.pg.eti.sab.util

/**
 * 
 * @author Filip Rogaczewski
 */
sealed class Message

case object CrawlerFinishedMessage extends Message
case object CloseMessage extends Message