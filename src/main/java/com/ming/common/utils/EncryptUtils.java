package com.ming.common.utils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptUtils extends EncryptRes{

    /**
     * �Ը������ַ�ʹ��md5���м��ܣ����ؼ����Ժ���ַ���
     *
     * @param origin Ҫ���ܵ��ַ���
     * @return ���ؼ��ܹ����ַ���
     */
    public static String getMd5(String origin) {
        // 1) ʹ�þ�̬����������MessageDigest����
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 2) ���ַ���ʹ��utf-8���б��룬�õ��ֽ�����
            byte[] input = origin.getBytes("utf-8");
            // 3) ʹ��digest(input)���ֽ��������md5�Ĺ�ϣ���㣬�õ������Ժ���ֽ����飬������16���ֽڡ�
            byte[] num = md.digest(input);
            // 4) ʹ����BigInteger(1, ���ܺ���ֽ�����)�����������������ת���޷��ŵĴ�����
            // 1 ������ -1��ʾ����
            BigInteger big = new BigInteger(1, num);
            // 5) �����������ת��16�����ַ���������Ϊ���ٽ���
            return big.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * SHA1���� , ��ȫ����� , �������㷨
     * @param str ��Ҫ���ܵ��ַ���
     * @return
     */
    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return "";
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * �������ӽ���
     * @param str ��Ҫ���ܻ�����Ҫ���ܵ��ַ���
     * @return
     */
    public static String xorDEcrypt(String str) {
        return xorDEcrypt(str, 1024);
    }

    /**
     * �������ӽ���
     * @param str ��Ҫ���ܻ�����Ҫ���ܵ��ַ���
     * @param key   ��Կ
     * @return
     */
    public static String xorDEcrypt(String str,int key) {
        String res = "";
        char[] c = str.toCharArray();
        for(int i=0;i<c.length;i++) {
            res += (char) (c[i]^key)+"";	//^Ϊ�����ţ�keyĬ������1024
        }
        return res;
    }

    /**
     * BASE64����
     * @param xmlStr ��Ҫ���ܵ��ַ���
     * @return
     */
    public static String base64Encrypt(String xmlStr) {
        byte[] encrypt = null;

        try {
            // ȡ��Ҫ�������ݵ�utf-8���롣
            encrypt = xmlStr.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // ȡMD5Hash�룬����ϼ�������
        byte[] md5Hasn = null;
        try {
            md5Hasn = MD5Hash(encrypt, 0, encrypt.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // �����Ϣ��
        byte[] totalByte = addMD5(md5Hasn, encrypt);

        // ȡ��Կ��ƫת����
        byte[] key = new byte[8];
        byte[] iv = new byte[8];
        getKeyIV(EncryptUtils.key, key, iv);
        SecretKeySpec deskey = new SecretKeySpec(key, "DES");
        IvParameterSpec ivParam = new IvParameterSpec(iv);

        // ʹ��DES�㷨ʹ�ü�����Ϣ��
        byte[] temp = null;
        try {
            temp = DES_CBC_Encrypt(totalByte, deskey, ivParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ʹ��Base64���ܺ󷵻�
        return Base64Utils.encodeToString(temp);
    }

    /**
     * BASE64����
     * @param xmlStr ��Ҫ���ܵ��ַ���
     * @return
     * @throws Exception
     */
    public static String base64Decrypt(String xmlStr) throws Exception {
        // base64����
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encBuf = null;
        try {
            encBuf = decoder.decode(xmlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ȡ��Կ��ƫת����
        byte[] key = new byte[8];
        byte[] iv = new byte[8];
        getKeyIV(EncryptUtils.key, key, iv);

        SecretKeySpec deskey = new SecretKeySpec(key, "DES");
        IvParameterSpec ivParam = new IvParameterSpec(iv);

        // ʹ��DES�㷨����
        byte[] temp = null;
        try {
            temp = DES_CBC_Decrypt(encBuf, deskey, ivParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ���н��ܺ��md5HashУ��
        byte[] md5Hash = null;
        try {
            md5Hash = MD5Hash(temp, 16, temp.length - 16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ���н���У��
        for (int i = 0; i < md5Hash.length; i++) {
            if (md5Hash[i] != temp[i]) {
                // System.out.println(md5Hash[i] + "MD5У�����" + temp[i]);
                throw new Exception("MD5У�����");
            }
        }

        // ���ؽ��ܺ�����飬����ǰ16λMD5Hash��Ҫ��ȥ��
        return new String(temp, 16, temp.length - 16, "utf-8");
    }

    /**
     * ʹ��AES��ʽ���� - ʹ��Ĭ�Ϸ�ʽ
     * @param content ��������
     * @return  ʮ�����Ƶ�����
     */
    public static String AseEncrypt(String content) {
        return AseEncrypt(content,ASE_DUFAULT);
    }

    /**
     * ʹ��AES��ʽ���� - ʹ��Ĭ�Ϸ�ʽ
     * @param content ʮ�����Ƶ�����
     * @return  ����
     */
    public static String AseDecrypt(String content) {
        return AseDecrypt(content,ASE_DUFAULT);
    }

    /**
     * ʹ��AES��ʽ����
     * @param content ��������
     * @param password ������Ҫ�õ���Կ��
     * @return  ʮ�����Ƶ�����
     */
    public static String AseEncrypt(String content, String password) {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // ����AES��Key������
            kgen.init(128, random);// �����û�������Ϊ�������ʼ����
            // 128λ��key������
            //����û��ϵ��SecureRandom�����ɰ�ȫ��������У�password.getBytes()�����ӣ�ֻҪ������ͬ�����о�һ�������Խ���ֻҪ��password����
            SecretKey secretKey = kgen.generateKey();// �����û����룬����һ����Կ
            byte[] enCodeFormat = secretKey.getEncoded();// ���ػ��������ʽ����Կ���������Կ��֧�ֱ��룬�򷵻�
            // null��
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// ת��ΪAESר����Կ
            Cipher cipher = Cipher.getInstance("AES");// ����������
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// ��ʼ��Ϊ����ģʽ��������

            byte[] result = cipher.doFinal(byteContent);// ����

            //����ʮ�����Ƶ�����
            return parseByte2HexStr(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ʹ��AES��ʽ����
     * @param content ʮ�����Ƶ�����
     * @param password ������Ҫ�õ���Կ��
     * @return ����
     */
    public static String AseDecrypt(String content, String password) {
        try {
            //��ʮ�����Ƶ�����ת���ɶ����Ƶ�����
            byte[] con = parseHexStr2Byte(content);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// ����AES��Key������
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();// �����û����룬����һ����Կ
            byte[] enCodeFormat = secretKey.getEncoded();// ���ػ��������ʽ����Կ
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// ת��ΪAESר����Կ
            Cipher cipher = Cipher.getInstance("AES");// ����������
            cipher.init(Cipher.DECRYPT_MODE, key);// ��ʼ��Ϊ����ģʽ��������
            byte[] result = cipher.doFinal(con);
            return new String(result); // ����

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

