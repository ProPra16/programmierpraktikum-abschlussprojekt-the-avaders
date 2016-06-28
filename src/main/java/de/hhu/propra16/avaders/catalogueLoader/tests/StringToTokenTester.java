package de.hhu.propra16.avaders.catalogueLoader.tests;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringToToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.Token;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class StringToTokenTester {
	 @Test
	 public void test_ExercisesString(){
		 Token token = null;
		 try {
			 token = StringToToken.convert("exercises", 1);
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
			token = StringToToken.convert("/exercises", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("/exercises", token.name);
		Assert.assertEquals(null, token.value);
	}

	@Test
	public void test_ClassesString(){
		Token token = null;
		try {
			token = StringToToken.convert("classes", 1);
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
			token = StringToToken.convert("/classes", 1);
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
			token = StringToToken.convert("tests", 1);
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
			token = StringToToken.convert("/tests", 1);
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
			token = StringToToken.convert("test name= \t \"testName\"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("test", token.name);
		Assert.assertEquals("testName", token.value);
	}

	@Test
	public void test_ConfigString(){
		Token token = null;
		try {
			token = StringToToken.convert("config", 1);
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
			token = StringToToken.convert("/config", 1);
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
			token = StringToToken.convert("exercise \t name=\"Römische Zahlen\"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("exercise", token.name);
		Assert.assertEquals("Römische Zahlen", token.value);
	}

	@Test
	public void test_Class(){
		Token token = null;
		try {
			token = StringToToken.convert("class name = \"test\"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		Assert.assertEquals("class", token.name);
		Assert.assertEquals("test", token.value);
	}

	@Test
	public void test_BabystepsString_false(){
		Token token = null;
		try {
			token = StringToToken.convert("babysteps value=\"  false \t \"", 1);
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
			token = StringToToken.convert("babysteps value=\"true\" time = \"2:00\"", 1);
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
			token = StringToToken.convert("babysteps \t time = \"2:00\"   value=\"true\" \n", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		Assert.assertEquals("babysteps", token.name);
		Assert.assertEquals("true", token.value);
		Assert.assertEquals("2:00", ((BabyStepsToken)token).time);
	}

	@Test
	public void test_timeTrackingString_false(){
		Token token = null;
		try {
			token = StringToToken.convert("timetracking value=\"  false \t \"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		Assert.assertEquals("timetracking", token.name);
		Assert.assertEquals("false", token.value);
	}

	@Test
	public void test_timeTrackingString_true(){
		Token token = null;
		try {
			token = StringToToken.convert("timetracking value=\"  true \t \"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		Assert.assertEquals("timetracking", token.name);
		Assert.assertEquals("true", token.value);
	}

	@Test
	public void test_timeTrackingString_wrongProperty(){
		try {
			StringToToken.convert("timetracking time=\"  true \t \"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

		fail();
	}

	@Test
	public void test_SamePropertyTwice_time(){
		try {
			StringToToken.convert("babysteps \t time = \"2:00\"   time=\"2:00\" \n", 1);
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
			StringToToken.convert("babysteps \t value = \"true\"   value=\"false\" \n", 1);
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
			StringToToken.convert("exercise = \"fail\"", 1);
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
