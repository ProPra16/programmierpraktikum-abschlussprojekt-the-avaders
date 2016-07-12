package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;

import java.io.IOException;

/**
 * A CatalogueLoader represents a source that returns an ExerciseCatalogue
 */
public interface CatalogueLoader {
	/**
	 * Loads Exercises from a source and returns them in an {@link ExerciseCatalogue} instance
	 * @return An {@link ExerciseCatalogue} holding {@link Exercise} instances
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
	 * @throws ParserException If an unexpected circumstance is occurs during parsing
     */
	ExerciseCatalogue loadCatalogue() throws SamePropertyTwiceException, IOException, TokenException, ParserException;
}
