package utilities;

public class ServerConfig {


	public static final String CHARACTERS_NAME_IMAGE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-*";
	public static final int LENGTH_NAME_IMAGE = 25;
	
	
	public static String getPATH_UPLOAD() {
		return System.getenv("IMG_PATH")+"/";
	}

	public static String getPATH_UPLOAD_PRIVATE() {
		return System.getenv("IMG_PRIVATE_PATH")+"/";
	}

	public static String getURL_IMAGE() {
		return System.getenv("URL_IMG_HOST")+"/";
	}

	public static String getURL_IMAGE_PRIVATE() {
		return System.getenv("URL_IMG_PRIVATE_HOST")+"/";
	}
	
	public static boolean getTesting(){
		boolean res;
		
		res = !(System.getenv("testing")==null || !System.getenv("testing").equals("true"));
		
		return res;
	}
}
