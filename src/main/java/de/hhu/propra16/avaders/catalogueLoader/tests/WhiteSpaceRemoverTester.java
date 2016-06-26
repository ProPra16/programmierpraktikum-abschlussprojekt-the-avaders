package de.hhu.propra16.avaders.catalogueLoader.tests;

import org.junit.Assert;
import org.junit.Test;

import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.insertEscapedCurlyBracket;
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.removeWhiteSpace;

public class WhiteSpaceRemoverTester {
	@Test
	public void test_Space(){
		Assert.assertEquals("test", removeWhiteSpace("      test   "));
	}

	@Test
	public void test_Tab(){
		Assert.assertEquals("test", removeWhiteSpace("\t\t  \t test \t   "));
	}

	@Test
	public void test_LineBreak(){
		Assert.assertEquals("test", removeWhiteSpace("\t\t  \t test \t   \n"));
	}

	@Test
	public void test_curly(){
		Assert.assertEquals("\\{\\}\\{\\}", insertEscapedCurlyBracket("{}{}"));
	}
}
