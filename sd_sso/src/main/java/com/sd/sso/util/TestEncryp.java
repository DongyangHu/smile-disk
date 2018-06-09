package com.sd.sso.util;

import com.sd.common.encryp.EncrypConstants;
import com.sd.common.encryp.EncryptAES;

public class TestEncryp {

    public static void main(String[] args) {
        String content = "YWY52099hdy";
        String encrypKey = EncrypConstants.AES_DECRYPT_KEY;
        String encryptStr = EncryptAES.encryptAES(content, encrypKey);
        System.out.println("加密后：" + encryptStr);
        System.out.println("解密后：" + EncryptAES.decryptAES(encryptStr, encrypKey));

    }

}
