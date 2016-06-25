package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.StringParser;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class StringParserTester {
	 @Test
	 public void test_ExercisesString(){
		 Token token = null;
		 try {
			 token = StringParser.parseToken("exercises", 1);
		 } catch (Exception e) {
			 System.out.println(e.getMessage());
			 fail();
		 }

		 Assert.assertEquals("exercises", token.name);
		 Assert.assertEquals(null, token.value);
	 }

	@Test
	public void test_SlashExercisesString(){
		Token token = null;
		try {
			token = StringParser.parseToken("/exercises", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("/exercises", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_DescriptionString(){
		Token token = null;
		try {
			token = StringParser.parseToken("description", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("description", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_SlashDescriptionString(){
		Token token = null;
		try {
			token = StringParser.parseToken("/description", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("/description", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_ClassesString(){
		Token token = null;
		try {
			token = StringParser.parseToken("classes", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("classes", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_SlashClassesString(){
		Token token = null;
		try {
			token = StringParser.parseToken("/classes", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("/classes", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_TestsString(){
		Token token = null;
		try {
			token = StringParser.parseToken("tests", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("tests", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_SlashTestsString(){
		Token token = null;
		try {
			token = StringParser.parseToken("/tests", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("/tests", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_TestString(){
		Token token = null;
		try {
			token = StringParser.parseToken("test", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("test", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_SlashTestString(){
		Token token = null;
		try {
			token = StringParser.parseToken("/test", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("/test", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_ConfigString(){
		Token token = null;
		try {
			token = StringParser.parseToken("config", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("config", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_SlashConfigString(){
		Token token = null;
		try {
			token = StringParser.parseToken("/config", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		Assert.assertEquals("/config", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_ExerciseNameString(){
		Token token = null;
		try {
			token = StringParser.parseToken("exercise \t name=\"Römische Zahlen\"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("exercise name", token.name);
		Assert.assertEquals("Römische Zahlen", token.value);
	}

	@Test
	public void test_Class(){
		Token token = null;
		try {
			token = StringParser.parseToken("class", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		Assert.assertEquals("class", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_BabystepsString_false(){
		Token token = null;
		try {
			token = StringParser.parseToken("babysteps value=\"  false \t \"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		Assert.assertEquals("babysteps", token.name);
		Assert.assertEquals("false", token.value);
	}

	@Test
	public void test_BabystepsString_true(){
		Token token = null;
		try {
			token = StringParser.parseToken("babysteps value=\"true\" time = \"2:00\"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("babysteps", token.name);
		Assert.assertEquals("true", token.value);
		Assert.assertEquals("2:00", ((BabyStepsToken)token).time);
	}

	@Test
	public void test_BabystepsString_true_switched(){
		Token token = null;
		try {
			token = StringParser.parseToken("babysteps \t time = \"2:00\"   value=\"true\" \n", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		Assert.assertEquals("babysteps", token.name);
		Assert.assertEquals("true", token.value);
		Assert.assertEquals("2:00", ((BabyStepsToken)token).time);
	}

	@Test
	public void test_SamePropertyTwice_time(){
		try {
			StringParser.parseToken("babysteps \t time = \"2:00\"   time=\"2:00\" \n", 1);
		} catch (SamePropertyTwiceException e) {
			System.out.println(e.getMessage());
			return;
		} catch (Exception e){
			fail();
		}
		fail();
	}

	@Test
	public void test_SamePropertyTwice_value(){
		try {
			StringParser.parseToken("babysteps \t value = \"true\"   value=\"false\" \n", 1);
		} catch (SamePropertyTwiceException e) {
			System.out.println(e.getMessage());
			return;
		}
		catch (Exception e){
			fail();
		}
		fail();
	}

	@Test
	public void test_Exercisename_missing_name(){
		try {
			StringParser.parseToken("exercise = \"fail\"", 1);
		} catch (MissingTokenException e) {
			System.out.println(e.getMessage());
			return;
		}
		catch (Exception e){
			fail();
		}
		fail();
	}
}
