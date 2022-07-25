package com.chen.space.Utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author zhao
 * @Date 2022/7/25 10:51
 */
public class AESUtils2 {
    /**
     * 密钥  AES加密要求key必须要128个比特位（这里需要长度为16，否则会报错）
     */
    private static final String KEY = "eyJhbGciOiJIUzI1";

    /**
     * 算法，CB模式（默认）：
     * 电码本模式    Electronic Codebook Book
     * CBC模式：
     * 密码分组链接模式    Cipher Block Chaining
     * CTR模式：
     * 计算器模式    Counter
     * CFB模式：
     * 密码反馈模式    Cipher FeedBack
     * OFB模式：
     * 输出反馈模式    Output FeedBack
     */
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    public static void main(String[] args) throws Exception {
        String content = "url：findNames.action";
        System.out.println("加密前：" + content);

        System.out.println("加密密钥和解密密钥：" + KEY);

//        String encrypt = aesEncrypt(content, KEY);
//        System.out.println("加密后：" + encrypt);C

        String encrypt = "8d6ce0a1ad1a7da3ea83180e43e17f9e916955deca0d4843a0bcf3a9d102cfd706578ed5cb9a337c7c0cf37905fc513536ca276ec24920f04e76d22953a3b126d6d1fbbf47132067a7c074497484f2728709d5a7376682886489c234418dac935c4f506bd25e46985f069219d094272b222f5c16262267f7443242516de50a1cfc34ef6455f1482ce6d9a8873557c58039c2ba8a23ad29c7c7eccbb9224a6fff";

        String decrypt = aesDecrypt(encrypt, KEY);

        System.out.println("解密后：" + decrypt);
    }

    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    private static String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception 抛出异常
     */
    private static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }


    /**
     * AES加密
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     */
    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }


    /**
     * AES加密为base 64 code
     *
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     */
    private static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey 解密密钥
     * @return 解密后的String
     */
    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }


    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     */
    private static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }


}
