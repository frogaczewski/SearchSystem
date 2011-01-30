package pl.gda.pg.eti.sab.search;

import java.util.List;

/**
 * @author Filip Rogaczewski
 */
public interface SearchEngine {

	List<SearchResult> search(String term);

}
