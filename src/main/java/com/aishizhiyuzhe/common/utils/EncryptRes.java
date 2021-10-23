package com.aishizhiyuzhe.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class EncryptRes {

    public static final int KEY_1 = 409821;
    public static final int KEY_2 = 10942;
    public static final int KEY_3 = 876430;
    public static final String ASE_DUFAULT = "@ASE%->@666";
    public static String key = "LmMGStGtOpF4xNyvYt54EQ==";

    protected static byte[] MD5Hash(byte[] buf, int offset, int length)
            throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(buf, offset, length);
        return md.digest();
    }

    protected static byte[] addMD5(byte[] md5Byte, byte[] bodyByte) {
        int length = bodyByte.length + md5Byte.length;
        byte[] resutlByte = new byte[length];

        // ǰ16λ��MD5Hash��
        for (int i = 0; i < length; i++) {
            if (i < md5Byte.length) {
                resutlByte[i] = md5Byte[i];
            } else {
                resutlByte[i] = bodyByte[i - md5Byte.length];
            }
        }
        return resutlByte;
    }

    protected static byte[] DES_CBC_Encrypt(byte[] sourceBuf, SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {
        byte[] cipherByte;
        // ʹ��DES�ԳƼ����㷨��CBCģʽ����
        Cipher encrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");

        encrypt.init(Cipher.ENCRYPT_MODE, deskey, ivParam);
        cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);
        // ���ؼ��ܺ���ֽ�����
        return cipherByte;
    }

    protected static void getKeyIV(String encryptKey, byte[] key, byte[] iv) {
        // ��ԿBase64����
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] buf = null;
        buf = decoder.decode(encryptKey);
        // ǰ8λΪkey
        int i;
        for (i = 0; i < key.length; i++) {
            key[i] = buf[i];
        }
        // ��8λΪiv����
        for (i = 0; i < iv.length; i++) {
            iv[i] = buf[i + 8];
        }
    }

    protected static byte[] DES_CBC_Decrypt(byte[] sourceBuf, SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {

        byte[] cipherByte;
        // ���Cipherʵ����ʹ��CBCģʽ��
        Cipher decrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // ��ʼ������ʵ��������Ϊ���ܹ��ܣ���������Կ��ƫת����
        decrypt.init(Cipher.DECRYPT_MODE, deskey, ivParam);

        cipherByte = decrypt.doFinal(sourceBuf, 0, sourceBuf.length);
        // ���ؽ��ܺ���ֽ�����
        return cipherByte;
    }

    //ʮ������ת��������
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    //������ת��ʮ������
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

}
