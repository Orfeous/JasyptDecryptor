package hu.orfeous.jasyptdecryptor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

/**
 *
 * @author gabor
 */
public class JasyptDecryptor {

    private static final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    private static Properties prop;

    public static void main(String[] args) throws IOException {
        prop = readPropertiesFile("decryptor.properties");
        encryptor.setAlgorithm(prop.getProperty("algorithm"));
        encryptor.setPassword(prop.getProperty("password"));
        encryptor.setIvGenerator(new RandomIvGenerator());
        //encryptor.setSaltGenerator(new ZeroSaltGenerator());
        //encryptor.setKeyObtentionIterations(10000);

        if (prop.getProperty("mode").equalsIgnoreCase("encrypt")) {
            System.out.println("Encryption Mode");
            String encryptedMsg = encryptor.encrypt(prop.getProperty("plaintext"));
            System.out.println("Output: " + encryptedMsg);
        } else {
            System.out.println("Decryption Mode");
            String decryptedMsg = encryptor.decrypt(prop.getProperty("hash"));
            System.out.println("Output: " + decryptedMsg);
        }
    }

    private static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }
}
