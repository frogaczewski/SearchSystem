package pl.gda.pg.eti.sab.searchengine.client.util;

/**
 * @author Paweł Ogrodowczyk
 *
 * @param <T> Typ, dla którego generowany będzie Html.
 */
public interface IHtmlRenderer<T> {

	String render(T object);
}
