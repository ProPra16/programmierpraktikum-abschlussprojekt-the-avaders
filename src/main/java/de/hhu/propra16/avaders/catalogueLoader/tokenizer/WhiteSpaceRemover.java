package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

public class WhiteSpaceRemover {
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
}
