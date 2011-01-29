package pl.gda.pg.eti.sab.search;

import java.util.List;

/**
 * @author Filip Rogaczewski
 */
public interface SearchEngine {

	List<Object> search(String term);

}
