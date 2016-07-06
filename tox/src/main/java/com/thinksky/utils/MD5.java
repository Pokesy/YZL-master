package com.thinksky.utils;

import android.support.annotation.NonNull;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jiao on 2016/4/29.
 */
public class MD5 {
    private static final String TAG = "MD5Encoder";
    private static String key = "QCdwsAeliJjXzVKOn1af0237cTUEqpmWhZkIYuHP";
    public static String md5(@NonNull String text) {
        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(encryptToSHA(text).getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            LogUtils.d( "password ===> " + strBuf.toString());

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            //LogUtils.d(  "", e);
            return "";
        } catch (UnsupportedEncodingException e) {
            //LogUtils.d(  "", e);
            return "";
        }
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }
    //SHA1 加密实例
    public static String encryptToSHA(String info) {
        byte[] digesta = null;
        try {
            // 得到一个SHA-1的消息摘要
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            // 添加要进行计算摘要的信息
            alga.update(info.getBytes());
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        String rs = byte2hex(digesta);
        return rs + key;
    }
}
