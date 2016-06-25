package de.hhu.propra16.avaders.catalogueLoader.tests;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.LineReader;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

public class XMLExerciseLoaderTester {
	@Test
	public void test(){
		LineReader lineReader;
		lineReader = new FileReader();
		try {
			XMLExerciseTokenizer xmlExerciseTokenizer = new XMLExerciseTokenizer();
		} catch (SamePropertyTwiceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TokenException e) {
			e.printStackTrace();
		}
	}
}
