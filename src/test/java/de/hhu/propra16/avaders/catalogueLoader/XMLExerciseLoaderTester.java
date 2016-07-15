package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.FileReader;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class XMLExerciseLoaderTester {
	@Test
	public void test_TestXml_Exercise0() {
		Exercise exercise = null;
		try {
			exercise = setup(0, "src/test/resources/test.xml");
		} catch (SamePropertyTwiceException | IOException | TokenException | ParserException e) {
			e.printStackTrace();
			fail();
		}

		// check exercise 0
		// exercise
		assertEquals("Roemische Zahlen", exercise.getExerciseName());
		assertEquals("Konvertiert arabische in roemische Zahlen.", exercise.getDescription());

		// source code
		assertEquals("RomanNumberConverter", exercise.getClassName(0));
		assertEquals("RomanNumberConverter2", exercise.getClassName(1));
		assertEquals("RomanNumberConverter3", exercise.getClassName(2));
		assertEquals("public class RomanNumberConverter {\n}", exercise.getClassTemplate(0));
		assertEquals("public class RomanNumberConverter2 {\n}", exercise.getClassTemplate(1));
		assertEquals("public class RomanNumberConverter3 {\n}", exercise.getClassTemplate(2));

		// test code
		assertEquals("RomanNumberConverterTest", exercise.getTestName(0));
		assertEquals("RomanNumberConverter2Test", exercise.getTestName(1));
		assertEquals("import static org.junit.Assert.*;\n" +
							"import org.junit.Test;\n" +
							"public class RomanNumberConverterTest {\n" +
							"\t@Test\n" +
							"\tpublic void testSomething() {\n" +
							"\t}\n" +
							"}", 									exercise.getTestTemplates(0));
		assertEquals("import static org.junit.Assert.*;\n" +
				"import org.junit.Test;\n" +
				"public class RomanNumberConverter2Test {\n" +
				"\t@Test\n" +
				"\tpublic void testSomethingElse() {\n" +
				"\t}\n" +
				"}", 									exercise.getTestTemplates(1));


		assertEquals(false, exercise.getExerciseConfig().isBabySteps());
		assertEquals(true, exercise.getExerciseConfig().isTimeTracking());
		assertEquals(false, exercise.getExerciseConfig().isAtdd());
	}

	@Test
	public void test_TestXml_Exercise1() {
		Exercise exercise = null;
		try {
			exercise = setup(1, "src/test/resources/test.xml");
		} catch (SamePropertyTwiceException | IOException | TokenException | ParserException e) {
			e.printStackTrace();
			fail();
		}

		// check exercise 1
		assertEquals("Arabische Zahlen", exercise.getExerciseName());
		assertEquals("Konvertiert roemische in arabische Zahlen.", exercise.getDescription());

		// source code
		assertEquals("ArabNumberConverter", exercise.getClassName(0));
		assertEquals("public class ArabNumberConverter {\n}", exercise.getClassTemplate(0));

		// test code
		assertEquals("ArabNumberConverterTest", exercise.getTestName(0));
		assertEquals("import static org.junit.Assert.*;\n" +
				"import org.junit.Test;\n" +
				"public class ArabNumberConverterTest {\n" +
				"\t@Test\n" +
				"\tpublic void testSomething() {\n" +
				"\t}\n" +
				"}", 									exercise.getTestTemplates(0));

		assertEquals(false, exercise.getExerciseConfig().isAtdd());
		assertEquals(true, exercise.getExerciseConfig().isBabySteps());
		assertEquals(120, exercise.getExerciseConfig().getBabyStepsTime());
		assertEquals(true, exercise.getExerciseConfig().isTimeTracking());
	}

	@Test
	public void test_TestXml_ExerciseLineBreaks() {
		Exercise exercise = null;
		try {
			exercise = setup(0, "src/test/resources/testLotsOfLineBreaks.xml");
		} catch (SamePropertyTwiceException | IOException | TokenException | ParserException e) {
			e.printStackTrace();
			fail();
		}

		// check exercise 0
		// exercise
		assertEquals("Roemische Zahlen", exercise.getExerciseName());
		assertEquals("Konvertiert arabische in roemische Zahlen.", exercise.getDescription());

		// source code
		assertEquals("RomanNumberConverter", exercise.getClassName(0));
		assertEquals("RomanNumberConverter2", exercise.getClassName(1));
		assertEquals("RomanNumberConverter3", exercise.getClassName(2));
		assertEquals("public class RomanNumberConverter {\n}", exercise.getClassTemplate(0));
		assertEquals("public class RomanNumberConverter2 {\n}", exercise.getClassTemplate(1));
		assertEquals("public class RomanNumberConverter3 {\n}", exercise.getClassTemplate(2));

		// test code
		assertEquals("RomanNumberConverterTest", exercise.getTestName(0));
		assertEquals("RomanNumberConverter2Test", exercise.getTestName(1));
		assertEquals("import static org.junit.Assert.*;\n" +
				"import org.junit.Test;\n" +
				"public class RomanNumberConverterTest {\n" +
				"\t@Test\n" +
				"\tpublic void testSomething() {\n" +
				"\t}\n" +
				"}", 									exercise.getTestTemplates(0));
		assertEquals("import static org.junit.Assert.*;\n" +
				"import org.junit.Test;\n" +
				"public class RomanNumberConverter2Test {\n" +
				"\t@Test\n" +
				"\tpublic void testSomethingElse() {\n" +
				"\t}\n" +
				"}", 									exercise.getTestTemplates(1));


		assertEquals(false, exercise.getExerciseConfig().isBabySteps());
		assertEquals(true, exercise.getExerciseConfig().isTimeTracking());
	}

	@Test
	public void test_TestXml_MissingEndToken() {
		MissingTokenException missingTokenException = new MissingTokenException("</test>", 26);

		try {
			setup(0, "src/test/resources/testMissingEndToken.xml");
		}catch(MissingTokenException e){
			assertEquals(missingTokenException.getMessage(), e.getMessage());
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
			exercise = setup(0, "src/test/resources/testEmptyConfig.xml");
		} catch (IOException | SamePropertyTwiceException | TokenException | ParserException e) {
			e.printStackTrace();
			fail();
		}

		assertEquals(false, exercise.getExerciseConfig().isBabySteps());
		assertEquals(false, exercise.getExerciseConfig().isTimeTracking());
		assertEquals(false, exercise.getExerciseConfig().isAtdd());
	}

	@Test
	public void test_TestXml_StandardBabySteps() {
		Exercise exercise = null;
		try {
			exercise = setup(0, "src/test/resources/testStandardBabySteps.xml");
		}catch(MissingTokenException e){
			System.out.println(e.getMessage());
			return;
		} catch (IOException | SamePropertyTwiceException | TokenException | ParserException e) {
			e.printStackTrace();
			fail();
		}

		assertEquals(true, exercise.getExerciseConfig().isBabySteps());
		assertEquals(120, exercise.getExerciseConfig().getBabyStepsTime());
		assertEquals(false, exercise.getExerciseConfig().isTimeTracking());
		assertEquals(false, exercise.getExerciseConfig().isAtdd());
	}

	private Exercise setup(int exerciseNr, String path)
			throws SamePropertyTwiceException, IOException, TokenException, ParserException {
		FileReader lineReader;
		XMLExerciseTokenizer xmlExerciseTokenizer;
		lineReader = new FileReader(Paths.get(path));
		ExerciseCatalogue exerciseCatalogue;

		xmlExerciseTokenizer = new XMLExerciseTokenizer(lineReader);
		CatalogueLoader exerciseCatalogueLoader = new ExerciseCatalogueLoader(xmlExerciseTokenizer, new ExerciseCatalogue());
		exerciseCatalogue = exerciseCatalogueLoader.loadCatalogue();

		return exerciseCatalogue.getExercise(exerciseNr);
	}
}
