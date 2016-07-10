package de.hhu.propra16.avaders.gui;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Batman140 on 10.07.2016.
 */
public class PathTools {

	public static Path getLeafPath(TreeItem<String> leaf){
		if(leaf.isLeaf() == false){
			System.out.println(leaf.getValue() + " is no leaf");
			return null;
		}
		String path = leaf.getValue() + ".txt";
		TreeItem<String> parent = leaf.getParent();
		while(parent != null){
			path   = parent.getValue() + File.separator + path;
			parent = parent.getParent();
		}
		return Paths.get(path);
	}

	//TODO control
	public static Path getPath(TreeItem<String> item){
		String path = item.getValue();;
		if(item.isLeaf()){
			path += ".txt";
		}
		TreeItem<String> parent = item.getParent();
		while(parent != null){
			path = parent.getValue() + File.separator + path;
			parent = parent.getParent();
		}
		return Paths.get(path);
	}

}
