package com.sd.fm.util;

import com.sd.common.encryp.EncrypConstants;
import com.sd.common.encryp.EncryptAES;

public class EncryptTest {

    public static void main(String[] args) {
        String dbUser = "sdweb";
        String dbPwd = "12345";
        
        String user = EncryptAES.encryptAES(dbUser, EncrypConstants.AES_DECRYPT_KEY);
        String pwd = EncryptAES.encryptAES(dbPwd, EncrypConstants.AES_DECRYPT_KEY);
        
        System.out.println(user);
        System.out.println(pwd);
    }

}
