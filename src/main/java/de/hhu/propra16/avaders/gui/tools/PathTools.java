package de.hhu.propra16.avaders.gui.tools;

import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTools {

	//finished
	public static Path getPath(TreeItem<String> item){
		String path = item.getValue();
		if(item.isLeaf()){
			path += ".java";
		}
		TreeItem<String> parent = item.getParent();
		while(parent != null){
			path = parent.getValue() + File.separator + path;
			parent = parent.getParent();
		}
		return Paths.get(path);
	}

	////TODO create exception
	public static String getFileNamePrefix(Path path, String suffix){
		if(!path.toString().endsWith(suffix)) {
			System.err.println("Path '" + path.toString() + "' does not end with suffix '" + suffix +"'");
			throw new RuntimeException();
		}
		return path.getFileName().toString().replace(suffix,"");
	}

	//finished
	public static String getFileNamePrefix(TreeItem<String> item, String suffix){
		return getFileNamePrefix(getPath(item), suffix);
	}

	//finished
	public static boolean hasParentName(TreeItem<String> item, String parentName){
		return PathTools.getPath(item).getParent().getFileName().toString().contentEquals(parentName);
	}

	//TODO create exception
	public static Path getDescriptionPath(TreeItem<String> item){
		Path itemPath = PathTools.getPath(item);
		if(itemPath.getNameCount() < 2) {
			System.err.println("Bad structure of directories. Has to be: root->exercise-> <Files> and description.txt");
			throw new RuntimeException();
		}
		return Paths.get(itemPath.subpath(0,2) + File.separator + "description.txt");
	}

	//finished
	public static String getNameOutOfPath(TreeItem<String> item, int depth){
		Path itemPath = getPath(item);
		int  pathSize = itemPath.getNameCount();
		if(pathSize < depth + 1)
			return itemPath.getFileName().toString();
		return getPath(item).getName(depth).toString();
	}

	//IMPORTANT: the name of test/class MUST be the same as in the file (by convention)
	public static boolean checkCatalogue(ExerciseCatalogue catalogue, TextArea informationOutputArea) {
		for (int i = 0; i < catalogue.size(); i++) {
			Exercise exercise = catalogue.getExercise(i);
			for (int j = 0; j < exercise.getNumberOfTests(); j++) {
				String testName = exercise.getTestName(j);
				for (int k = 0; k < exercise.getNumberOfClasses(); k++) {
					String className = exercise.getClassName(k);
					if (testName.endsWith("Test") & className.contentEquals(testName.replace("Test", ""))) {
						break;
					}
					if (k == exercise.getNumberOfClasses() - 1){
						informationOutputArea.setText("Not able to load catalogue. \nReason: Could not find class '" + testName.replace("Test",".java") + "" +
								"' for testclass '"+ testName +".java' \nFor every test xxxTest.java must exists a class xxx.java ");
						return false;
					}
				}
			}
		}
		return true;
	}
}
