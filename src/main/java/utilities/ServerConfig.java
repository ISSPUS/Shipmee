package utilities;

public interface ServerConfig {

	public final String PATH_UPLOAD = "/home/usuario/workspace/Shipmee/src/main/webapp/images/";
	public final String PATH_UPLOAD_PRIVATE = "/home/usuario/workspace/Shipmee/src/main/webapp/images/private";

	public final String URL_IMAGE = "http://localhost:8080/Shipmee/images/";
	public final String URL_IMAGE_PRIVATE = "http://localhost:8080/Shipmee/images/private";

	public final String CHARACTERS_NAME_IMAGE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-*";
	public final int LENGTH_NAME_IMAGE = 25;

}
