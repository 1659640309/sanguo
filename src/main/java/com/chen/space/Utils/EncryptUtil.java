package com.chen.space.Utils;

import org.apache.commons.codec.binary.Hex;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author zhao
 * @Date 2022/7/25 10:29
 */
public class EncryptUtil {


    //这个密钥需要是16位
    public static final String KEY_DES = "eyJhbGciOiJIUzI1";

    public static void main(String[] args) throws Exception {
        String old = "123";
        String target ="8d6ce0a1ad1a7da3ea83180e43e17f9e916955deca0d4843a0bcf3a9d102cfd706578ed5cb9a337c7c0cf37905fc513536ca276ec24920f04e76d22953a3b126d6d1fbbf47132067a7c074497484f2728709d5a7376682886489c234418dac935c4f506bd25e46985f069219d094272b222f5c16262267f7443242516de50a1cfc34ef6455f1482ce6d9a8873557c58039c2ba8a23ad29c7c7eccbb9224a6fff";
//        Hex.decodeHex(target);
        //解密
//        System.out.println(EncryptUtil.aesDecryptForFront(target,EncryptUtil.KEY_DES));

        //加密
        System.out.println(EncryptUtil.aesEncryptForFront(old,EncryptUtil.KEY_DES));

    }

    /**
     * AES解密
     * @param encryptStr 密文
     * @param decryptKey 秘钥，必须为16个字符组成
     * @return 明文
     * @throws Exception
     */
    public static String aesDecryptForFront(String encryptStr, String decryptKey) {
        if (StringUtils.isEmpty(encryptStr) || StringUtils.isEmpty(decryptKey)) {
            return null;
        }
        try {
            byte[] encryptByte = Base64.getDecoder().decode(encryptStr);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
            byte[] decryptBytes = cipher.doFinal(encryptByte);
            return new String(decryptBytes);

        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }


    }

    /**
     * AES加密
     * @param content 明文
     * @param encryptKey 秘钥，必须为16个字符组成
     * @return 密文
     * @throws Exception
     */
    public static String aesEncryptForFront(String content, String encryptKey) {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(encryptKey)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

            byte[] encryptStr = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptStr);

        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }

    }

}

