package com.example.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.Security;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/9
 */
public class FileProcessing {
    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDigestSM3(File file) throws Exception {
        //System.out.println(file.getAbsolutePath());
        try (InputStream fis = new FileInputStream(file)) {
            byte buffer[] = new byte[1024];
            MessageDigest md = MessageDigest.getInstance("SM3");
            for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
                md.update(buffer, 0, numRead);
            }
            return Hex.toHexString(md.digest());
        }
    }
}
