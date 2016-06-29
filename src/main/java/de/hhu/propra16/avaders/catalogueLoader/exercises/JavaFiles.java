package de.hhu.propra16.avaders.catalogueLoader.exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the java files of the exercise
 */
public class JavaFiles {
	private List<JavaFile>	javaFiles;

	public JavaFiles(){
		javaFiles = new ArrayList<>();
	}

	public void addJavaFile(JavaFile javaFile){
		javaFiles.add(javaFile);
	}

	public JavaFile getJavaFile(int javaFileNumber){
		return javaFiles.get(javaFileNumber);
	}

	public int size() {
		return javaFiles.size();
	}
}
