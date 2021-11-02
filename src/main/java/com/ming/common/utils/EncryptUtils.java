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
     * 对给定的字符使用md5进行加密，返回加密以后的字符串
     *
     * @param origin 要加密的字符串
     * @return 返回加密过的字符串
     */
    public static String getMd5(String origin) {
        // 1) 使用静态方法，创建MessageDigest对象
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 2) 将字符串使用utf-8进行编码，得到字节数组
            byte[] input = origin.getBytes("utf-8");
            // 3) 使用digest(input)对字节数组进行md5的哈希计算，得到加密以后的字节数组，长度是16个字节。
            byte[] num = md.digest(input);
            // 4) 使用类BigInteger(1, 加密后的字节数组)，将这个二进制数组转成无符号的大整数
            // 1 正数， -1表示负数
            BigInteger big = new BigInteger(1, num);
            // 5) 将这个大整数转成16进制字符串，参数为多少进制
            return big.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * SHA1加密 , 安全性最高 , 不可逆算法
     * @param str 需要加密的字符串
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
     * 异或运算加解密
     * @param str 需要加密或者需要解密的字符串
     * @return
     */
    public static String xorDEcrypt(String str) {
        return xorDEcrypt(str, 1024);
    }

    /**
     * 异或运算加解密
     * @param str 需要加密或者需要解密的字符串
     * @param key   公钥
     * @return
     */
    public static String xorDEcrypt(String str,int key) {
        String res = "";
        char[] c = str.toCharArray();
        for(int i=0;i<c.length;i++) {
            res += (char) (c[i]^key)+"";	//^为异或符号，key默认设置1024
        }
        return res;
    }

    /**
     * BASE64加密
     * @param xmlStr 需要加密的字符串
     * @return
     */
    public static String base64Encrypt(String xmlStr) {
        byte[] encrypt = null;

        try {
            // 取需要加密内容的utf-8编码。
            encrypt = xmlStr.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 取MD5Hash码，并组合加密数组
        byte[] md5Hasn = null;
        try {
            md5Hasn = MD5Hash(encrypt, 0, encrypt.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 组合消息体
        byte[] totalByte = addMD5(md5Hasn, encrypt);

        // 取密钥和偏转向量
        byte[] key = new byte[8];
        byte[] iv = new byte[8];
        getKeyIV(EncryptUtils.key, key, iv);
        SecretKeySpec deskey = new SecretKeySpec(key, "DES");
        IvParameterSpec ivParam = new IvParameterSpec(iv);

        // 使用DES算法使用加密消息体
        byte[] temp = null;
        try {
            temp = DES_CBC_Encrypt(totalByte, deskey, ivParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 使用Base64加密后返回
        return Base64Utils.encodeToString(temp);
    }

    /**
     * BASE64解密
     * @param xmlStr 需要解密的字符串
     * @return
     * @throws Exception
     */
    public static String base64Decrypt(String xmlStr) throws Exception {
        // base64解码
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encBuf = null;
        try {
            encBuf = decoder.decode(xmlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 取密钥和偏转向量
        byte[] key = new byte[8];
        byte[] iv = new byte[8];
        getKeyIV(EncryptUtils.key, key, iv);

        SecretKeySpec deskey = new SecretKeySpec(key, "DES");
        IvParameterSpec ivParam = new IvParameterSpec(iv);

        // 使用DES算法解密
        byte[] temp = null;
        try {
            temp = DES_CBC_Decrypt(encBuf, deskey, ivParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 进行解密后的md5Hash校验
        byte[] md5Hash = null;
        try {
            md5Hash = MD5Hash(temp, 16, temp.length - 16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 进行解密校检
        for (int i = 0; i < md5Hash.length; i++) {
            if (md5Hash[i] != temp[i]) {
                // System.out.println(md5Hash[i] + "MD5校验错误。" + temp[i]);
                throw new Exception("MD5校验错误。");
            }
        }

        // 返回解密后的数组，其中前16位MD5Hash码要除去。
        return new String(temp, 16, temp.length - 16, "utf-8");
    }

    /**
     * 使用AES方式加密 - 使用默认方式
     * @param content 加密内容
     * @return  十六进制的密文
     */
    public static String AseEncrypt(String content) {
        return AseEncrypt(content,ASE_DUFAULT);
    }

    /**
     * 使用AES方式解密 - 使用默认方式
     * @param content 十六进制的密文
     * @return  明文
     */
    public static String AseDecrypt(String content) {
        return AseDecrypt(content,ASE_DUFAULT);
    }

    /**
     * 使用AES方式加密
     * @param content 加密内容
     * @param password 解密需要用到的钥匙
     * @return  十六进制的密文
     */
    public static String AseEncrypt(String content, String password) {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 创建AES的Key生产者
            kgen.init(128, random);// 利用用户密码作为随机数初始化出
            // 128位的key生产者
            //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            // null。
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密

            //返回十六进制的密文
            return parseByte2HexStr(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用AES方式解密
     * @param content 十六进制的密文
     * @param password 解密需要用到的钥匙
     * @return 明文
     */
    public static String AseDecrypt(String content, String password) {
        try {
            //把十六进制的密文转换成二进制的密文
            byte[] con = parseHexStr2Byte(content);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
            byte[] result = cipher.doFinal(con);
            return new String(result); // 明文

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

