package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.springframework.util.Assert;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ImageUpload {

	public static String subirImagen(CommonsMultipartFile imagen,String path) throws Exception {
		
		String nameImage = getNameImage();
		File directorio = new File(path);
    	File localFile = new File(path + nameImage);
    	Assert.isTrue(directorio.getFreeSpace()>1500000000, "message.error.imageUpload.freeSpace");
    	FileOutputStream os = null;
    	String tipoArchivo = imagen.getContentType();
    	Assert.isTrue(tipoArchivo.equals("image/jpg")|| tipoArchivo.equals("image/jpeg")||tipoArchivo.equals("image/png"),"message.error.imageUpload.incompatibleType");
    	try {
    		
    		os = new FileOutputStream(localFile);
    		os.write(imagen.getBytes());

    	} finally {
    		if (os != null) {
    			try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
		return nameImage;

	}
	public static String getNameImage() {
        String CARACTERES = ServerConfig.CHARACTERS_NAME_IMAGE;
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < ServerConfig.LENGTH_NAME_IMAGE) {
            int index = (int) (rnd.nextFloat() * CARACTERES.length());
            salt.append(CARACTERES.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
