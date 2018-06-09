package com.sd.common.encryp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EncryptRSA {
    private static Log logger = LogFactory.getLog(EncryptRSA.class);

    private static KeyPair keyPair = null;
    private static Cipher  cipher = null;
    private static Cipher  cipherEncrypt = null;
    private final static String CIPHER_ALGORITHM = "RSA/None/PKCS1Padding";
    private final static String KEYPAIR_PATH = "RSAKey.txt";
    
    static {
        try {
            getKeyPair();
        } catch (Exception e) {
            logger.error("",e);
        }
        
    }
    public static String getPublicKey () throws ClassNotFoundException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        getKeyPair();
        PublicKey key =  keyPair.getPublic();
        String publicKey = new String(Base64.encodeBase64(key.getEncoded()));
        return publicKey;
    }
    

    /**
     * 加密
     * @param str
     * @return
     * @throws Exception
     */
    public static String encrypt(String str) throws Exception {
        return encrypt(str,null);
    }

    /**
     * 加密
     * @param str
     * @param keyPairFileName
     * @return
     * @throws Exception
     */
    private synchronized static String encrypt(String str,String keyPairFileName) throws Exception {
        getKeyPair();
        byte [] b = cipherEncrypt.doFinal(str.getBytes("utf-8"));
        return Base64.encodeBase64String(b);
    }
    

    /**
     * 解密
     * @param encryptStr
     * @return
     * @throws Exception
     */
    public static String decrypt (String encryptStr) throws Exception {
        return decrypt(encryptStr, null);
    }
    
    /**
     * 解密
     * @param encryptStr
     * @param keyPairFileName
     * @return
     * @throws Exception
     */
    private synchronized static String decrypt (String encryptStr,String keyPairFileName) throws Exception {
        getKeyPair();
        byte[] plainText = cipher.doFinal(Base64.decodeBase64(encryptStr));
        String str1 = new String (plainText);
        return str1;
    }
    

    /**
     * 生成keyPair文件
     * @param storeFilePath
     * @return
     * @throws Exception
     */
    public static String createRSAKeyPair (String storeFilePath) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        keyPairGen.initialize(1024, random);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey key = (RSAPublicKey) keyPair.getPublic();
        String publicKey = new String(Base64.encodeBase64(key.getEncoded()));
        FileOutputStream fos = new FileOutputStream(storeFilePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(keyPair);
        oos.close();
        fos.close();
        
        return publicKey;
    }
    
    public synchronized static void getKeyPair () throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if(keyPair == null) {
            logger.info("getKeyPair加载文件"+KEYPAIR_PATH);
            InputStream inStream = EncryptRSA.class.getClassLoader().getResourceAsStream(KEYPAIR_PATH);
            ObjectInputStream ois = new ObjectInputStream(inStream);
            keyPair = (KeyPair) ois.readObject();
            ois.close();
            inStream.close();
            
        }
        if(cipher == null){
            org.bouncycastle.jce.provider.BouncyCastleProvider provider =   new org.bouncycastle.jce.provider.BouncyCastleProvider();
            cipher = Cipher.getInstance(CIPHER_ALGORITHM, provider);
            RSAPrivateKey pbk = (RSAPrivateKey)keyPair.getPrivate();
            cipher.init(Cipher.DECRYPT_MODE, pbk);
        }
        
        if(cipherEncrypt == null){
            PublicKey publicKey = keyPair.getPublic();
            cipherEncrypt = Cipher.getInstance(CIPHER_ALGORITHM, new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipherEncrypt.init(Cipher.ENCRYPT_MODE, publicKey);
        }
    }
    
    public static void main(String[] args){
        try {
            String encrypt = encrypt("1111");
            String decrypt = decrypt(encrypt("1111"));
            System.out.println(encrypt);
            System.out.println(decrypt);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
