package utilities;

import java.text.Normalizer;

public class ViewsMethods {

	public static String normalizeUrl(String input){
		String res;
		
		res = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		
		return res;
	}
}
