package de.hhu.propra16.avaders.gui;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Batman140 on 10.07.2016.
 */
public class PathTools {

	public static Path getPath(TreeItem<String> item){
		String path = item.getValue();;
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

	public static Path getDescriptionPath(TreeItem<String> item){
		Path itemPath = PathTools.getPath(item);
		//no guarantee that file exists
		if(itemPath.getNameCount() < 2) {
			System.err.println("Bad structure of directories. Has to be: root->exercise-> <Files> and description.txt");
			return null;
		}
		return Paths.get(itemPath.subpath(0,2) + File.separator + "description.txt");
	}

	public static String getFileNamePrefix(Path path, String suffix){
		return path.getFileName().toString().replace(suffix,"");
	}

	public static boolean hasParentName(TreeItem<String> item, String parentName){
		if(PathTools.getPath(item).getParent().getFileName().toString().contentEquals(parentName))
			return true;
		return false;
	}

}
