package de.hhu.propra16.avaders.extensions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cookiebowser on 04.07.2016.
 */
public class TrackingTestDummy extends Tracking {
	private List<String> exceptionDummys = new ArrayList<>();

	public TrackingTestDummy(){
		exceptionDummys.add("invalid method declaration; return type required");
		exceptionDummys.add("';' expected");
		exceptionDummys.add("reached end of file while parsing");
		exceptionDummys.add("illegal start of expression");
		exceptionDummys.add("cannot find symbol");
		exceptionDummys.add("incompatible types: java.lang.String cannot be converted to int");
		exceptionDummys.add("<identifier> expected");
		exceptionDummys.add("class, interface, or enum expected");
		exceptionDummys.add("illegal start of type");
		exceptionDummys.add("method <Method> in class <Class> cannot be applied to given types");
		exceptionDummys.add("not a statement");
		exceptionDummys.add("incompatible types: int[][] cannot be converted to java.lang.String[][]");
	}

	public void generateRandomData(){
		secondsGREEN = (int)(Math.random()*150);
		secondsRED = (int)(Math.random()*150);
		secondsREFACTOR = (int)(Math.random()*150);
		secondsREFACTOR2 = (int)(Math.random()*150);

		int x = 0;
		while (x < 12){
			compileErrorMapRED.put(exceptionDummys.get(x),(int)(Math.random()*20));
			compileErrorMapREFACTOR.put(exceptionDummys.get(x),(int)(Math.random()*20));
			x++;
		}
	}
}
