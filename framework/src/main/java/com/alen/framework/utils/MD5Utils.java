package com.alen.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 */
public class MD5Utils {
    public static String encode(String text) {

        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(text.getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);

                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }

                sb.append(hexString);
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getVirusMd5(String appPath) {
        try {
            File file = new File(appPath);

            FileInputStream fis = new FileInputStream(file);

            int len = -1;
            byte[] buffer = new byte[1024];
            StringBuffer sb = new StringBuffer();
            MessageDigest instance = MessageDigest.getInstance("MD5");
            while ((len = fis.read(buffer)) != -1) {

                instance.update(buffer);
            }
            byte[] digest = instance.digest();
            for (byte b : digest) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);

                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }

                sb.append(hexString);
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
