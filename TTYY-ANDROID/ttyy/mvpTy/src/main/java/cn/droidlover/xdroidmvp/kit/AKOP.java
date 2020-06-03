package cn.droidlover.xdroidmvp.kit;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Cap_Sub
 * AES CBC加密
 */
public class AKOP {



    /**
     * 加密
     *
     * @param sSrc
     * @return
     * @throws Exception
     */
    public static String Encrypt(String password,String sSrc,String algorithm,String transformation) {
        //"算法/模式/补码方式"
        Cipher cipher = null;
        try {
            byte[] raw = password.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, algorithm);
            cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = new byte[0];
            encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            //此处使用BASE64做转码功能，同时能起到2次加密的作用。
            String resultStr = parseByte2HexStr(encrypted);
            return resultStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解密
    public static String Decrypt(String password,String sSrc,String algorithm,String transformation) {
        try {
            //先用base64解密
            byte[] contentFrom = parseHexStr2Byte(sSrc);
            byte[] raw = password.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            try {
                byte[] original = cipher.doFinal(contentFrom);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     * @throws
     * @method parseByte2HexStr
     * @since v1.0
     */
    public static String parseByte2HexStr(byte[] buf) {
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

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     * @throws
     * @method parseHexStr2Byte
     * @since v1.0
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
