package de.hhu.propra16.avaders.catalogueLoader.tests;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.WhiteSpaceRemover;
import org.junit.Assert;
import org.junit.Test;

public class WhiteSpaceRemoverTester {
	@Test
	public void test_Space(){
		Assert.assertEquals("test", WhiteSpaceRemover.removeWhiteSpace("      test   "));
	}

	@Test
	public void test_Tab(){
		Assert.assertEquals("test", WhiteSpaceRemover.removeWhiteSpace("\t\t  \t test \t   "));
	}

	@Test
	public void test_LineBreak(){
		Assert.assertEquals("test", WhiteSpaceRemover.removeWhiteSpace("\t\t  \t test \t   \n"));
	}
}
