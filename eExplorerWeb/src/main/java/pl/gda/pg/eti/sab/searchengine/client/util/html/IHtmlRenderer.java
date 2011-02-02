package pl.gda.pg.eti.sab.searchengine.client.util.html;

/**
 * @author Paweł Ogrodowczyk
 *
 * @param <T> Typ, dla którego generowany będzie Html.
 */
public interface IHtmlRenderer<T extends Object> {

	String render(T object);
}
