package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

/**
 * Holds different methods for manipulating strings
 */
public class StringOperations {
	/**
	 * removes all whitespace characters from the beginning
	 * and end of the given string
	 * @param string the string which will be modified
	 * @return the string with all whitespace characters removed from its
	 * beginning and end
     */
	public static String removeWhiteSpace(String string){
		while(string.length() > 0 && Character.isWhitespace(string.charAt(0))){
			string = string.substring(1);
		}

		while(string.length() > 0
				&& Character.isWhitespace(
				string.charAt(string.length()-1))
				){
			string = string.substring(0, string.length()-1);
		}

		return string;
	}

	/**
	 * removes the first occurrence of the
	 * string specified with toRemove from the string removeFrom and removes
	 * whitespaces from beginning and end of the string
	 * @param removeFrom string which toRemove will be removed from
	 * @param toRemove string that will be removed from removeFrom
     * @return the removeFrom string with the toRemove substring and whitespaces
	 * removed
     */
	public static String remove(String removeFrom, String toRemove){
		return removeWhiteSpace(removeFrom.replaceFirst(toRemove,""));
	}

	/**
	 * Replaces all {, } with their escaped variant \{, \}
	 * @param string in which the {, } are to be replaced
	 * @return the string with all {, } escaped
     */
	public static String insertEscapedCurlyBracket(String string){
		return string.replace("{", "\\{").replace("}", "\\}");
	}
}
