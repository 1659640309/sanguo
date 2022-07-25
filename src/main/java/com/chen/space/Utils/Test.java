package com.chen.space.Utils;


import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * @Author zhao
 * @Date 2022/7/25 11:20
 */


public class Test {


    private final static String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTgzNTkzNDAsInVzZXJuYW1lIjoieWdieXlkcyJ9.9Z-y85M6TQYBZaoysWvY3nOHD4fJenhNfzOO0JZtq50";

    /**
     *加密操作
     * @param content  待加密内容
     * @param encodingFormat 编码方式
     * @param sKey 密码
     * @param ivParameter 偏移量
     * @return
     * @throws Exception
     */
    public String encrypt(String content, String encodingFormat, String sKey, String ivParameter) {
        try {
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            while (content.getBytes(encodingFormat).length % 16 != 0){
                content+=" ";
            }
            byte[] encrypted = cipher.doFinal(content.getBytes(encodingFormat));
            return Hex.encodeHexString(encrypted).toUpperCase(Locale.ROOT);
        }catch (Exception e){
            System.out.println("encrypt错误");
        }
        return null;
    }

    /**
     *解密操作
     * @param content 待解密内容
     * @param encodingFormat 编码方式
     * @param sKey 密码
     * @param ivParameter 偏移量
     * @return
     * @throws Exception
     */
    public String decrypt(String content, String encodingFormat, String sKey, String ivParameter) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, DecoderException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {

            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] bytes = Hex.decodeHex(content);
            byte[] original = cipher.doFinal(bytes);
            String originalString = new String(original, encodingFormat);
            return originalString;
    }

    public String getSkey(){
        return TOKEN.substring(0,16);
    }

    public String getIv(){
        return TOKEN.substring(0,16);
    }

    public static void main(String[] args) throws DecoderException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        Test test = new Test();

        String content = "8d6ce0a1ad1a7da3ea83180e43e17f9e916955deca0d4843a0bcf3a9d102cfd706578ed5cb9a337c7c0cf37905fc513536ca276ec24920f04e76d22953a3b126d6d1fbbf47132067a7c074497484f2728709d5a7376682886489c234418dac935c4f506bd25e46985f069219d094272b222f5c16262267f7443242516de50a1cfc34ef6455f1482ce6d9a8873557c58039c2ba8a23ad29c7c7eccbb9224a6fff";
        String ecodingFormat = "UTF-8";
        String skey = test.getSkey();
        String iv = test.getIv();
        String decrypt = test.decrypt(content, ecodingFormat, skey, iv);
        System.out.println(decrypt);
    }

}

