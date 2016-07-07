package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.FileReader;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

public class XMLExerciseTokenizerTester {
	private XMLExerciseTokenizer xmlExerciseTokenizer = null;
	@Test
	public void test_BeginExercisesToken(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "     < \t  exercises  > \n");
			xmlExerciseTokenizer.advance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("exercises", xmlExerciseTokenizer.currentToken().name);
		assertEquals(null, xmlExerciseTokenizer.currentToken().value);
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

		assertEquals("/exercises", xmlExerciseTokenizer.currentToken().name);
		assertEquals(null, xmlExerciseTokenizer.currentToken().value);
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

		assertEquals(false, xmlExerciseTokenizer.hasNextToken());
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

		assertEquals("test Description", token.value);
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

		assertEquals("test Description", token.value);
	}


	@Test
	public void test_BeginClasses(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <       classes >\n");
			xmlExerciseTokenizer.advance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("classes", xmlExerciseTokenizer.currentToken().name);
	}

	@Test
	public void test_EndClasses(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          </		classes >\n");
			xmlExerciseTokenizer.advance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("/classes", xmlExerciseTokenizer.currentToken().name);
	}

	@Test
	public void test_ExerciseName(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  exercise    name \t = \"test\" >\n");
			xmlExerciseTokenizer.advance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("exercise", xmlExerciseTokenizer.currentToken().name);
		assertEquals("test", xmlExerciseTokenizer.currentToken().value);
	}

	@Test
	public void test_BeginBabySteps_missingQuote(){
		MissingTokenException missingTokenException = new MissingTokenException("\" around false", 1);

		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  babysteps value = \t \"false \n />\n");
			xmlExerciseTokenizer.advance();
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
	public void test_BeginBabySteps_missingEquals(){
		MissingTokenException missingTokenException = new MissingTokenException("=", 1);
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  babysteps value  \t \"false\" \n />\n");
			xmlExerciseTokenizer.advance();
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
	public void test_BeginBabySteps_false(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  babysteps value = \t \"false\" \n />\n");
			xmlExerciseTokenizer.advance();
		}
		catch (Exception e){
			fail();
		}

		assertEquals("babysteps", xmlExerciseTokenizer.currentToken().name);
		assertEquals("false", xmlExerciseTokenizer.currentToken().value);
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

		assertEquals("babysteps", xmlExerciseTokenizer.currentToken().name);
		assertEquals("true", xmlExerciseTokenizer.currentToken().value);
		assertEquals(135, ((BabyStepsToken)xmlExerciseTokenizer.currentToken()).time);
	}

	@Test
	public void test_BeginBabySteps_missing_time(){
		UnexpectedTokenException unexpectedTokenException =
				new UnexpectedTokenException("property: time or value", "= \"2:15\"", 1);

		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  babysteps value = \t \"true\" = \"2:15\" />\n");
			xmlExerciseTokenizer.advance();
		}
		catch (TokenException e){
			assertEquals(unexpectedTokenException.getMessage(), e.getMessage());
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
		UnexpectedTokenException unexpectedTokenException =
				new UnexpectedTokenException("<exercises>, </exercises>, <description>, </description> \n" +
				"<classes>, </classes>, <tests>, </tests>, </test>, <config> or </config>", "  \t / test   \t\t ", 1);

		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          <  \t / test   \t\t >\n");
			xmlExerciseTokenizer.advance();
		}
		catch (UnexpectedTokenException e){
			assertEquals(unexpectedTokenException.getMessage(), e.getMessage());
			return;
		}
		catch (Exception e){
			fail();
		}

		fail();
	}


	@Test
	public void test_EndTest(){
		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(() -> "          </  \t test   \t\t >\n");
			xmlExerciseTokenizer.advance();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			fail();
		}

		assertEquals("/test", xmlExerciseTokenizer.currentToken().name);
	}

	@Test
	public void test_WrongExtension(){
		IOException ioException = new IOException("Please choose a file with the \".xml\" extension.");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(Paths.get("src/test/java/de/hhu/propra16/avaders/catalogueLoader/test.txt"));
		} catch (IOException | ParserException e) {
			e.printStackTrace();
		}

		try {
			xmlExerciseTokenizer = new XMLExerciseTokenizer(fileReader);
		} catch (SamePropertyTwiceException | TokenException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e){
			assertEquals(ioException.getMessage(), e.getMessage());
			return;
		}
		fail();
	}

}
