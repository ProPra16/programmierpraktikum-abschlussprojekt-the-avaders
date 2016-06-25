package de.hhu.propra16.avaders.catalogueLoader.tests;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.FileReader;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static junit.framework.TestCase.fail;

public class XMLExerciseLoaderTester {
	@Test
	public void test_test_xml() {
		FileReader lineReader;
		lineReader = new FileReader(Paths.get("java\\de\\hhu\\propra16\\avaders\\catalogueLoader\\tests\\test.xml"));
		try {
			XMLExerciseTokenizer xmlExerciseTokenizer = new XMLExerciseTokenizer(lineReader);
		} catch (SamePropertyTwiceException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (TokenException e) {
			e.printStackTrace();
			fail();
		}

		//TODO: continue until fully read
	}
}
