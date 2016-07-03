package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

/**
 * Holds different methods for manipulating strings
 */
class StringOperations {

	/**
	 * removes the first occurrence of the
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
}
