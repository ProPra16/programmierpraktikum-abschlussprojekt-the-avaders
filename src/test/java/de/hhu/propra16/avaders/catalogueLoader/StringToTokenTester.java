package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringToToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class StringToTokenTester {
	 @Test
	 public void test_ExercisesString(){
		 Token token = null;
		 try {
			 token = StringToToken.getInstance().convert("exercises", 1);
		 } catch (Exception e) {
			 System.out.println(e.getMessage());
			 fail();
		 }

		 assertEquals("exercises", token.name);
		 assertEquals(null, token.value);
	 }

	@Test
	public void test_SlashExercisesString(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("/exercises", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("/exercises", token.name);
		assertEquals(null, token.value);
	}

	@Test
	public void test_ClassesString(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("classes", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("classes", token.name);
		assertEquals(null, token.value);
	}

	@Test
	public void test_SlashClassesString(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("/classes", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("/classes", token.name);
		assertEquals(null, token.value);
	}

	@Test
	public void test_TestsString(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("tests", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("tests", token.name);
		assertEquals(null, token.value);
	}

	@Test
	public void test_SlashTestsString(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("/tests", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("/tests", token.name);
		assertEquals(null, token.value);
	}

	@Test
	public void test_TestString(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("test name= \t \"testName\"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("test", token.name);
		assertEquals("testName", token.value);
	}

	@Test
	public void test_ConfigString(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("config", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("config", token.name);
		assertEquals(null, token.value);
	}

	@Test
	public void test_SlashConfigString(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("/config", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals("/config", token.name);
		assertEquals(null, token.value);
	}

	@Test
	public void test_ExerciseNameString(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("exercise \t name=\"Römische Zahlen\"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("exercise", token.name);
		assertEquals("Römische Zahlen", token.value);
	}

	@Test
	public void test_Class(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("class name = \"test\"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals("class", token.name);
		assertEquals("test", token.value);
	}

	@Test
	public void test_BabyStepsString_false(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("babysteps value=\"  false \t \"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals("babysteps", token.name);
		assertEquals("false", token.value);
	}


	@Test
	public void test_BabyStepsString_true(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("babysteps value=\"true\" time = \"2:00\"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("babysteps", token.name);
		assertEquals("true", token.value);
		assertEquals(120, ((BabyStepsToken)token).time);
	}

	@Test
	public void test_BabyStepsString_true_switched(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("babysteps \t time = \"2:00\"   value=\"true\" \n", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals("babysteps", token.name);
		assertEquals("true", token.value);
		assertEquals(120, ((BabyStepsToken)token).time);
	}

	@Test
	public void test_timeTrackingString_false(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("timetracking value=\"  false \t \"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals("timetracking", token.name);
		assertEquals("false", token.value);
	}

	@Test
	public void test_timeTrackingString_true(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("timetracking value=\"  true \t \"", 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals("timetracking", token.name);
		assertEquals("true", token.value);
	}

	@Test
	public void test_timeTrackingString_wrongProperty(){
		UnexpectedTokenException unexpectedTokenException =
				new UnexpectedTokenException("property: value", "time=\"  true \t \"", 1);

		try {
			StringToToken.getInstance().convert("timetracking time=\"  true \t \"", 1);
		} catch (Exception e) {
			assertEquals(unexpectedTokenException.getMessage(), e.getMessage());
			return;
		}
		fail();
	}

	@Test
	public void test_SamePropertyTwice_time(){
		SamePropertyTwiceException samePropertyTwiceException = new SamePropertyTwiceException("time", 1);

		try {
			StringToToken.getInstance().convert("babysteps \t time = \"2:00\"   time=\"2:00\" \n", 1);
		} catch (SamePropertyTwiceException e) {
			assertEquals(samePropertyTwiceException.getMessage(), e.getMessage());
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
			StringToToken.getInstance().convert("babysteps \t value = \"true\"   value=\"false\" \n", 1);
		} catch (SamePropertyTwiceException e) {
			assertEquals(samePropertyTwiceException.getMessage(), e.getMessage());
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
			StringToToken.getInstance().convert("exercise = \"fail\"", 1);
		} catch (MissingTokenException e) {
			assertEquals(missingTokenException.getMessage(), e.getMessage());
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
			token = StringToToken.getInstance().convert("atdd \t value = \"true\" \n", 1);
		} catch (Exception e){
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("atdd", token.name);
		assertEquals("true", token.value);
	}

	@Test
	public void test_Atdd_false(){
		Token token = null;
		try {
			token = StringToToken.getInstance().convert("atdd \t value = \"false\" \n", 1);
		} catch (Exception e){
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("atdd", token.name);
		assertEquals("false", token.value);
	}

	@Test
	public void test_Atdd_samePropertyTwice_value(){
		SamePropertyTwiceException samePropertyTwiceException = new SamePropertyTwiceException("value", 1);

		try {
			StringToToken.getInstance().convert("atdd \t value = \"false\" value = \"true\"\n", 1);
		} catch (SamePropertyTwiceException e){
			assertEquals(samePropertyTwiceException.getMessage(), e.getMessage());
			return;
		} catch (TokenException e) {
			e.printStackTrace();
			fail();
		}

		fail();
	}
}
