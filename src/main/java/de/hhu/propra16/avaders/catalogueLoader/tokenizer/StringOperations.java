package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

public class StringOperations {
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

	public static String remove(String removeFrom, String toRemove){
		return removeWhiteSpace(removeFrom.replaceFirst(toRemove,""));
	}

	public static String insertEscapedCurlyBracket(String string){
		return string.replace("{", "\\{").replace("}", "\\}");
	}
}
