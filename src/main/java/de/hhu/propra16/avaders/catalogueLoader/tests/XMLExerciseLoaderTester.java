package de.hhu.propra16.avaders.catalogueLoader.tests;

import de.hhu.propra16.avaders.catalogueLoader.ParserException;
import de.hhu.propra16.avaders.catalogueLoader.XMLExerciseLoader;
import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.FileReader;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static junit.framework.TestCase.fail;

public class XMLExerciseLoaderTester {
	@Test
	public void test_TestXml_Exercise0() {
		Exercise exercise = null;
		try {
			exercise = setup(0, "java/de/hhu/propra16/avaders/catalogueLoader/tests/test.xml");
		} catch (SamePropertyTwiceException | IOException | TokenException | ParserException e) {
			e.printStackTrace();
			fail();
		}

		// check exercise 0
		// exercise
		Assert.assertEquals("Römische Zahlen", exercise.getExerciseName());
		Assert.assertEquals("Konvertiert arabische in römische Zahlen.", exercise.getDescription());

		// source code
		Assert.assertEquals("RomanNumberConverter", exercise.getClassName(0));
		Assert.assertEquals("RomanNumberConverter2", exercise.getClassName(1));
		Assert.assertEquals("RomanNumberConverter3", exercise.getClassName(2));
		Assert.assertEquals("public class RomanNumberConverter {\n}", exercise.getClassTemplate(0));
		Assert.assertEquals("public class RomanNumberConverter2 {\n}", exercise.getClassTemplate(1));
		Assert.assertEquals("public class RomanNumberConverter3 {\n}", exercise.getClassTemplate(2));

		// test code
		Assert.assertEquals("RomanNumberConverterTest", exercise.getTestName(0));
		Assert.assertEquals("RomanNumberConverterTest2", exercise.getTestName(1));
		Assert.assertEquals("import static org.junit.Assert.*;\n" +
							"import org.junit.Test;\n" +
							"public class RomanNumbersTest {\n" +
							"@Test\n" +
							"public void testSomething() {\n" +
							"}\n" +
							"}", 									exercise.getTestTemplates(0));
		Assert.assertEquals("import static org.junit.Assert.*;\n" +
				"import org.junit.Test;\n" +
				"public class RomanNumbersTest2 {\n" +
				"@Test\n" +
				"public void testSomethingElse() {\n" +
				"}\n" +
				"}", 									exercise.getTestTemplates(1));


		Assert.assertEquals(false, exercise.babyStepsIsEnabled());
		Assert.assertEquals(true, exercise.timeTrackingIsEnabled());
		Assert.assertEquals(false, exercise.atdd());
	}

	@Test
	public void test_TestXml_Exercise1() {
		Exercise exercise = null;
		try {
			exercise = setup(1, "java/de/hhu/propra16/avaders/catalogueLoader/tests/test.xml");
		} catch (SamePropertyTwiceException | IOException | TokenException | ParserException e) {
			e.printStackTrace();
			fail();
		}

		// check exercise 1
		Assert.assertEquals("Arabische Zahlen", exercise.getExerciseName());
		Assert.assertEquals("Konvertiert römische in arabische Zahlen.", exercise.getDescription());

		// source code
		Assert.assertEquals("ArabNumberConverter", exercise.getClassName(0));
		Assert.assertEquals("public class ArabNumberConverter {\n}", exercise.getClassTemplate(0));

		// test code
		Assert.assertEquals("ArabNumberConverterTest", exercise.getTestName(0));
		Assert.assertEquals("import static org.junit.Assert.*;\n" +
				"import org.junit.Test;\n" +
				"public class ArabNumbersTest {\n" +
				"@Test\n" +
				"public void testSomething() {\n" +
				"}\n" +
				"}", 									exercise.getTestTemplates(0));

		Assert.assertEquals(true, exercise.babyStepsIsEnabled());
		Assert.assertEquals(true, exercise.timeTrackingIsEnabled());
		Assert.assertEquals("2:00", exercise.babyStepsTime());
		Assert.assertEquals(true, exercise.atdd());
	}

	@Test
	public void test_TestXml_ExerciseLineBreaks() {
		Exercise exercise = null;
		try {
			exercise = setup(0, "java/de/hhu/propra16/avaders/catalogueLoader/tests/testLotsOfLineBreaks.xml");
		} catch (SamePropertyTwiceException | IOException | TokenException | ParserException e) {
			e.printStackTrace();
			fail();
		}

		// check exercise 0
		// exercise
		Assert.assertEquals("Römische Zahlen", exercise.getExerciseName());
		Assert.assertEquals("Konvertiert arabische in römische Zahlen.", exercise.getDescription());

		// source code
		Assert.assertEquals("RomanNumberConverter", exercise.getClassName(0));
		Assert.assertEquals("RomanNumberConverter2", exercise.getClassName(1));
		Assert.assertEquals("RomanNumberConverter3", exercise.getClassName(2));
		Assert.assertEquals("public class RomanNumberConverter {\n}", exercise.getClassTemplate(0));
		Assert.assertEquals("public class RomanNumberConverter2 {\n}", exercise.getClassTemplate(1));
		Assert.assertEquals("public class RomanNumberConverter3 {\n}", exercise.getClassTemplate(2));

		// test code
		Assert.assertEquals("RomanNumberConverterTest", exercise.getTestName(0));
		Assert.assertEquals("RomanNumberConverterTest2", exercise.getTestName(1));
		Assert.assertEquals("import static org.junit.Assert.*;\n" +
				"import org.junit.Test;\n" +
				"public class RomanNumbersTest {\n" +
				"@Test\n" +
				"public void testSomething() {\n" +
				"}\n" +
				"}", 									exercise.getTestTemplates(0));
		Assert.assertEquals("import static org.junit.Assert.*;\n" +
				"import org.junit.Test;\n" +
				"public class RomanNumbersTest2 {\n" +
				"@Test\n" +
				"public void testSomethingElse() {\n" +
				"}\n" +
				"}", 									exercise.getTestTemplates(1));


		Assert.assertEquals(false, exercise.babyStepsIsEnabled());
		Assert.assertEquals(true, exercise.timeTrackingIsEnabled());
	}

	@Test
	public void test_TestXml_MissingEndToken() {
		MissingTokenException missingTokenException = new MissingTokenException("</test>", 26);

		try {
			setup(0, "java/de/hhu/propra16/avaders/catalogueLoader/tests/testMissingEndToken.xml");
		}catch(MissingTokenException e){
			Assert.assertEquals(missingTokenException.getMessage(), e.getMessage());
			return;
		} catch (IOException | SamePropertyTwiceException | TokenException | ParserException e) {
			e.printStackTrace();
			fail();
		}
		fail();
	}

	@Test
	public void test_TestXml_EmptyConfig() {
		Exercise exercise = null;
		try {
			exercise = setup(0, "java/de/hhu/propra16/avaders/catalogueLoader/tests/testEmptyConfig.xml");
		} catch (IOException | SamePropertyTwiceException | TokenException | ParserException e) {
			e.printStackTrace();
			fail();
		}

		Assert.assertEquals(false, exercise.babyStepsIsEnabled());
		Assert.assertEquals(false, exercise.atdd());
		Assert.assertEquals(false, exercise.timeTrackingIsEnabled());
	}

	@Test
	public void test_TestXml_StandardBabySteps() {
		Exercise exercise = null;
		try {
			exercise = setup(0, "java/de/hhu/propra16/avaders/catalogueLoader/tests/testStandardBabySteps.xml");
		}catch(MissingTokenException e){
			System.out.println(e.getMessage());
			return;
		} catch (IOException | SamePropertyTwiceException | TokenException | ParserException e) {
			e.printStackTrace();
			fail();
		}

		Assert.assertEquals(true, exercise.babyStepsIsEnabled());
		Assert.assertEquals("2:00", exercise.babyStepsTime());
		Assert.assertEquals(false, exercise.atdd());
		Assert.assertEquals(false, exercise.timeTrackingIsEnabled());
	}

	private Exercise setup(int exerciseNr, String path)
			throws SamePropertyTwiceException, IOException, TokenException, ParserException {
		FileReader lineReader;
		XMLExerciseTokenizer xmlExerciseTokenizer = null;
		lineReader = new FileReader(Paths.get(path));
		ExerciseCatalogue exerciseCatalogue = null;

		xmlExerciseTokenizer = new XMLExerciseTokenizer(lineReader);

		XMLExerciseLoader xmlExerciseLoader = new XMLExerciseLoader(xmlExerciseTokenizer, new ExerciseCatalogue());

		exerciseCatalogue = xmlExerciseLoader.loadExercise();

		return exerciseCatalogue.getExercise(exerciseNr);
	}
}
