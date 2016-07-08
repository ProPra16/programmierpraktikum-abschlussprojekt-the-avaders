package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import static java.lang.Integer.*;

/**
 * Holds different methods for manipulating strings
 */
class StringOperations {

	/**
	 * Removes the first occurrence of the
	 * string specified with toRemove from the string removeFrom and removes
	 * whitespaces from beginning and end of the string
	 * @param removeFrom string which toRemove will be removed from
	 * @param toRemove string that will be removed from removeFrom
     * @return the removeFrom string with the toRemove substring and whitespaces
	 * removed
     */
	static String remove(String removeFrom, String toRemove){
		return removeFrom.replaceFirst(toRemove,"").trim();
	}

	/**
	 * Replaces all {, } with their escaped variant \{, \}
	 * @param string in which the {, } are to be replaced
	 * @return the string with all {, } escaped
     */
	static String insertEscapedCurlyBracket(String string){
		return string.replace("{", "\\{").replace("}", "\\}");
	}

	/**
	 * Parses a string in form "m:s" where m are minutes and s are seconds
	 * and stores the amount of seconds these correspond to in an integer
	 * @param time The string that holds the time in form "m:s"
	 * @return An integer holding the time in seconds
     */
	static int StringTimeToSeconds(String time) throws NumberFormatException{
		if(time == null) return 0;
		int indexOfColon = time.indexOf(":");
		String minutes = time.substring(0, indexOfColon);
		String seconds = time.substring(indexOfColon+1);
		System.out.println(parseInt(minutes)*60 + parseInt(seconds));
		return parseInt(minutes)*60 + parseInt(seconds);
	}
}
