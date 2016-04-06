package br.com.ocupelago.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class UtilProperties {

	
    private static String PATH = "";
    private static File arquivo;
    private static FileInputStream fIS;

    /**
     * Open the FileInputStream for the Properties Files
     * 
     * @throws FileNotFoundException
     */
    public synchronized static void openFileInputStream() throws FileNotFoundException {
        arquivo = new File(PATH);
        fIS = new FileInputStream(arquivo);
    }

    /**
     * Close the the FileInputStream for the Properties Files
     * 
     * @throws IOException
     */
    public synchronized static void closeFileInputStream() throws IOException {
        fIS.close();
    }

    /**
     * Return the Properties Object based in Properties File
     * 
     * @return the Properties Object
     * @throws IOException
     */
    public synchronized static Properties readProperties() throws IOException {
        openFileInputStream();
        Properties ret = new Properties();
        ret.load(fIS);
        closeFileInputStream();
        return ret;
    }

    public static String getProperty(String propertyName) throws IOException {
        String ret = "";
        Properties props = readProperties();
        ret = props.getProperty(propertyName);
        return ret;
    }

    public static String getKSLocation() throws IOException {
        return UtilProperties.getProperty("ocupelago.keystore");
    }

	public static String getKSPassword() throws IOException {
		return UtilProperties.getProperty("ocupelago.keystore.password");
	}

	public static void setPATH(String pATH) {
		PATH = pATH;
	}

}

