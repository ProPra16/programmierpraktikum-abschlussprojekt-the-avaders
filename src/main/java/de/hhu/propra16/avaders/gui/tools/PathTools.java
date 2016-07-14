package de.hhu.propra16.avaders.gui.tools;

import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Mainly used to build a FileTree and for getting information out of TableView. Tools for handling TreeItems as Path
 */
public class PathTools {

	/**
	 * Get the Path for an in exercisesTree selected TreeItem
	 * @param item Selected item
	 * @return corresponding Path with xxxCatalogue as root
     */
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

	/**
	 * Determines the filename of a given path and removes .xxx appendix
	 * @param path    Path of the filename
	 * @param suffix  The .xxx appendix to remove
     * @return Filename without appendix .xxx
     */
	public static String getFileNamePrefix(Path path, String suffix){
		if(!path.toString().endsWith(suffix)) {
			System.err.println("Path '" + path.toString() + "' does not end with suffix '" + suffix +"'");
			throw new RuntimeException();
		}
		return path.getFileName().toString().replace(suffix,"");
	}

	/**
	 * Determines the filename of a given TreeItem and removes .xxx appendix
	 * @param item    TreeItem of find the filename of
	 * @param suffix  The .xxx appendix to remove
     * @return Filename of TreeItem without appendix .xxx
     */
	public static String getFileNamePrefix(TreeItem<String> item, String suffix){
		return getFileNamePrefix(getPath(item), suffix);
	}

	/**
	 * Determines whether a TreeItem has a parent with a certain name
	 * @param item        The TreeItem
	 * @param parentName  The certain parent-name to verify
     * @return true if the given parent-name could be verified, otherwise false
     */
	public static boolean hasParentName(TreeItem<String> item, String parentName){
		return PathTools.getPath(item).getParent().getFileName().toString().contentEquals(parentName);
	}

	/**
	 * Determines a valid path for a exercise-description according to a given TreeItem
	 * @param item Given TreeItem to determine a description-path to
	 * @return Path for the description-file
     */
	public static Path getDescriptionPath(TreeItem<String> item){
		Path itemPath = PathTools.getPath(item);
		if(itemPath.getNameCount() < 2) {
			System.err.println("Bad structure of directories. Has to be: root->exercise-> <Files> and description.txt");
			throw new RuntimeException();
		}
		return Paths.get(itemPath.subpath(0,2) + File.separator + "description.txt");
	}

	/**
	 * Gets a name within a path of a treeItem
	 * @param item  Given TreeItem
	 * @param depth The place for the name to be got
     * @return The name out of a path from a TreeItem according to a given depth
     */
	public static String getNameOutOfPath(TreeItem<String> item, int depth){
		Path itemPath = getPath(item);
		int  pathSize = itemPath.getNameCount();
		if(pathSize < depth + 1)
			return itemPath.getFileName().toString();
		return getPath(item).getName(depth).toString();
	}

	/**
	 * Veryfies that the given catalogue holds only test named xxxTest.java and has for each a corresponding class xxx.java
	 * @param catalogue              Given Catalogue to be load
	 * @param informationOutputArea  The area where a failure-message will be shown on
     * @return true if its a valid catalogue, otherwise false
     */
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
