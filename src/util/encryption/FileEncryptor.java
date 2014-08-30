package util.encryption;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 

import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
 
/**
 * A utility class that encrypts or decrypts a file.
 * @author www.codejava.net
 *
 */
public class FileEncryptor {
    //private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
 
    public static void encrypt(String username, String pass, File inputFile, File outputFile)
            throws CryptoException {
    	doCrypto(Cipher.ENCRYPT_MODE, username, pass, inputFile, outputFile);
    }
 
    public static void decrypt(String username, String pass, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, username, pass, inputFile, outputFile);
    }
 
    private static void doCrypto(int cipherMode, String username, String pass, File inputFile,
            File outputFile) throws CryptoException {
        try {
        	
        	byte[] key = (username + pass).getBytes("UTF-8");
        	MessageDigest sha = MessageDigest.getInstance("SHA-1");
        	key = sha.digest(key);
        	key = Arrays.copyOf(key, 16); // use only first 128 bit

        	Key secretKeySpec = new SecretKeySpec(key, "AES");
        	
            //Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            //cipher.init(cipherMode, secretKey);
            cipher.init(cipherMode, secretKeySpec);
             
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
             
            byte[] outputBytes = cipher.doFinal(inputBytes);
             
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
             
            inputStream.close();
            outputStream.close();
             
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }
}