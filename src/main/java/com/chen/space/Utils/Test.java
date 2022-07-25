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


    //登录token
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
            //密钥 -> 字节
            byte[] raw = sKey.getBytes();
            //加密方式
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            //加密的 类型\模式\填充
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //偏移量
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            //初始化
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            while (content.getBytes(encodingFormat).length % 16 != 0){
                content+=" ";
            }
            byte[] encrypted = cipher.doFinal(content.getBytes(encodingFormat));
            //转换成16进制吧（好像）
            return Hex.encodeHexString(encrypted).toUpperCase(Locale.ROOT);
        }catch (Exception e){
            System.out.println("encrypt错误");
        }
        return null;
    }

    /**
     *解密操作
     * @param content 待解密内容
     * @param encodingFormat 字符编码
     * @param sKey 密钥
     * @param ivParameter 偏移量
     * @return
     * @throws Exception
     */
    public String decrypt(String content, String encodingFormat, String sKey, String ivParameter)
            throws Exception{
        //密钥字节
        byte[] raw = sKey.getBytes("ASCII");
        //加密方式
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //加密的 类型\模式\填充
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //偏移量
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        //初始化
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        //解码
        byte[] bytes = Hex.decodeHex(content);
        byte[] original = cipher.doFinal(bytes);
        //设置字符编码
        String originalString = new String(original, encodingFormat);
        return originalString;
    }

    //取token前16位
    public String getSkey(){
        return TOKEN.substring(0,16);
    }

    //取token前16位
    public String getIv(){
        return TOKEN.substring(0,16);
    }

    public static void main(String[] args) throws Exception {

        Test test = new Test();

        String content = "8d6ce0a1ad1a7da3ea83180e43e17f9e916955deca0d4843a0bcf3a9d102cfd706578ed5cb9a337c7c0cf37905fc513536ca276ec24920f04e76d22953a3b126d6d1fbbf47132067a7c074497484f2728709d5a7376682886489c234418dac935c4f506bd25e46985f069219d094272b222f5c16262267f7443242516de50a1cfc34ef6455f1482ce6d9a8873557c58039c2ba8a23ad29c7c7eccbb9224a6fff";
        String ecodingFormat = "UTF-8";
        String skey = test.getSkey();
        String iv = test.getIv();
        String decrypt = test.decrypt(content, ecodingFormat, skey, iv);
        System.out.println(decrypt);
    }

}

