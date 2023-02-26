package com.sorapp.jowncms.jowncms.Function.Hash;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashService
{

    //Get Hash Text start
    public static String HashText(String text)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
            byte[] result = md.digest(text.getBytes());
            return new String(result);
        }
        catch (Exception e)
        {
            return "";
        }
    }
    //Get Hash Text end

    //Get hash file start
    public static String HashFile(File file)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (InputStream is = Files.newInputStream(file.toPath())) {
                try (DigestInputStream dis = new DigestInputStream(is, md)) {
                    /* Read decorated stream (dis) to EOF as normal... */
                }
            }
            byte[] digest = md.digest();
            return new String(digest.toString());
        }
        catch (Exception e)
        {
            System.out.println("Error Hash : "+e.getMessage());
            return "";
        }

    }
    //Get hash file end

}
