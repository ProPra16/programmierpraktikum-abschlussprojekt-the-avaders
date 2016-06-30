package de.hhu.propra16.avaders.catalogueLoader.tests;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.Token;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class XMLExerciseTokenizerTester {
	XMLExerciseTokenizer xmlExerciseTokenizer = null;
	@Test
	public void test_BeginExercisesToken(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "     < \t  exercises  > \n");
			xmlExerciseTokenizer.advance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("exercises", xmlExerciseTokenizer.currentToken().name);
		Assert.assertEquals(null, xmlExerciseTokenizer.currentToken().value);
	}

	@Test
	public void test_EndExercisesToken(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "</exercises>");
			xmlExerciseTokenizer.advance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("/exercises", xmlExerciseTokenizer.currentToken().name);
		Assert.assertEquals(null, xmlExerciseTokenizer.currentToken().value);
	}

	@Test
	public void test_EOF(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> null);
			xmlExerciseTokenizer.advance();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		Assert.assertEquals(false, xmlExerciseTokenizer.hasNextToken());
	}

	@Test
	public void test_Description(){
		Token token = null;
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <description> test Description      </description>\n");
			token = xmlExerciseTokenizer.readContent("description");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		Assert.assertEquals("test Description", token.value);
	}

	@Test
	public void test_DescriptionLineBreak(){
		Token token = null;
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <description> \n test Description  \n    </description>\n");
			token = xmlExerciseTokenizer.readContent("description");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		Assert.assertEquals("test Description", token.value);
	}

	//TODO: make work
	/*
	@Test
	public void test_ExerciseNameBeforeDescription(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "<exercise name = \"test\"> <description>   Konvertiert arabische in römische Zahlen.   </description >\n");
			xmlExerciseTokenizer.advance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("exercise name", xmlExerciseTokenizer.currentToken().name);
	}
	*/

	@Test
	public void test_BeginClasses(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <       classes >\n");
			xmlExerciseTokenizer.advance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("classes", xmlExerciseTokenizer.currentToken().name);
	}

	@Test
	public void test_EndClasses(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <       /classes >\n");
			xmlExerciseTokenizer.advance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("/classes", xmlExerciseTokenizer.currentToken().name);
	}

	@Test
	public void test_Exercisename(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  exercise    name \t = \"test\" >\n");
			xmlExerciseTokenizer.advance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("exercise", xmlExerciseTokenizer.currentToken().name);
		Assert.assertEquals("test", xmlExerciseTokenizer.currentToken().value);
	}

	@Test
	public void test_BeginBabySteps_missingQuote(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  babysteps value = \t \"false \n />\n");
			xmlExerciseTokenizer.advance();
		} catch (MissingTokenException e) {
			System.out.println(e.getMessage());
			return;
		}
		catch (Exception e){
			fail();
		}

		fail();
	}

	@Test
	public void test_BeginBabySteps_missingEquals(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  babysteps value  \t \"false\" \n />\n");
			xmlExerciseTokenizer.advance();
		} catch (MissingTokenException e) {
			System.out.println(e.getMessage());
			return;
		}
		catch (Exception e){
			fail();
		}

		fail();
	}

	@Test
	public void test_BeginBabySteps_false(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  babysteps value = \t \"false\" \n />\n");
			xmlExerciseTokenizer.advance();
		}
		catch (Exception e){
			fail();
		}

		Assert.assertEquals("babysteps", xmlExerciseTokenizer.currentToken().name);
		Assert.assertEquals("false", xmlExerciseTokenizer.currentToken().value);
	}

	@Test
	public void test_BeginBabySteps_true(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  babysteps value = \t \"true\" time = \"2:15\" \n />\n");
			xmlExerciseTokenizer.advance();
		}
		catch (Exception e){
			fail();
		}

		Assert.assertEquals("babysteps", xmlExerciseTokenizer.currentToken().name);
		Assert.assertEquals("true", xmlExerciseTokenizer.currentToken().value);
		Assert.assertEquals("2:15", ((BabyStepsToken)xmlExerciseTokenizer.currentToken()).time);
	}

	@Test
	public void test_BeginBabySteps_missing_time(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  babysteps value = \t \"true\" = \"2:15\" />\n");
			xmlExerciseTokenizer.advance();
		}
		catch (TokenException e){
			System.out.println(e.getMessage());
			return;
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			fail();
		}

		fail();
	}

	@Test
	public void test_UnknownToken(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  \t / test   \t\t >\n");
			xmlExerciseTokenizer.advance();
		}
		catch (UnexpectedTokenException e){
			System.out.println(e.getMessage());
			return;
		}
		catch (Exception e){
			fail();
		}

		fail();
	}

	/*
	@Test
	public void test_EndTest(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          </  \t test   \t\t >\n");
			xmlExerciseTokenizer.advance();
		}
		catch (Exception e){
			fail();
		}

		Assert.assertEquals("/test", xmlExerciseTokenizer.currentToken().name);
	}
	*/
}
