package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringToToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;
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
	public void test_BabyStepsString_false(){
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
	public void test_BabyStepsString_true(){
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
	public void test_BabyStepsString_true_switched(){
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
		UnexpectedTokenException unexpectedTokenException =
				new UnexpectedTokenException("property: value", "time=\"  true \t \"", 1);

		try {
			StringToToken.convert("timetracking time=\"  true \t \"", 1);
		} catch (Exception e) {
			Assert.assertEquals(unexpectedTokenException.getMessage(), e.getMessage());
			return;
		}
		fail();
	}

	@Test
	public void test_SamePropertyTwice_time(){
		SamePropertyTwiceException samePropertyTwiceException = new SamePropertyTwiceException("time", 1);

		try {
			StringToToken.convert("babysteps \t time = \"2:00\"   time=\"2:00\" \n", 1);
		} catch (SamePropertyTwiceException e) {
			Assert.assertEquals(samePropertyTwiceException.getMessage(), e.getMessage());
			return;
		} catch (Exception e){
			fail();
		}
		fail();
	}

	@Test
	public void test_SamePropertyTwice_value(){
		SamePropertyTwiceException samePropertyTwiceException = new SamePropertyTwiceException("value", 1);

		try {
			StringToToken.convert("babysteps \t value = \"true\"   value=\"false\" \n", 1);
		} catch (SamePropertyTwiceException e) {
			Assert.assertEquals(samePropertyTwiceException.getMessage(), e.getMessage());
			return;
		}
		catch (Exception e){
			fail();
		}
		fail();
	}

	@Test
	public void test_ExerciseName_missing_name(){
		MissingTokenException missingTokenException = new MissingTokenException("exercise, name or =", 1);

		try {
			StringToToken.convert("exercise = \"fail\"", 1);
		} catch (MissingTokenException e) {
			Assert.assertEquals(missingTokenException.getMessage(), e.getMessage());
			return;
		}
		catch (Exception e){
			fail();
		}
		fail();
	}

	@Test
	public void test_Atdd_true(){
		Token token = null;
		try {
			token = StringToToken.convert("atdd \t value = \"true\" \n", 1);
		} catch (Exception e){
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("atdd", token.name);
		Assert.assertEquals("true", token.value);
	}

	@Test
	public void test_Atdd_false(){
		Token token = null;
		try {
			token = StringToToken.convert("atdd \t value = \"false\" \n", 1);
		} catch (Exception e){
			System.out.println(e.getMessage());
			fail();
		}

		Assert.assertEquals("atdd", token.name);
		Assert.assertEquals("false", token.value);
	}

	@Test
	public void test_Atdd_samePropertyTwice_value(){
		SamePropertyTwiceException samePropertyTwiceException = new SamePropertyTwiceException("value", 1);

		try {
			StringToToken.convert("atdd \t value = \"false\" value = \"true\"\n", 1);
		} catch (SamePropertyTwiceException e){
			Assert.assertEquals(samePropertyTwiceException.getMessage(), e.getMessage());
			return;
		} catch (TokenException e) {
			e.printStackTrace();
			fail();
		}

		fail();
	}
}
