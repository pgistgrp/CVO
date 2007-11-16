package org.pgist.util;

import java.security.MessageDigest;


/**
 * MD5 class
 * @author kenny
 *
 */
public class MD5 {

    
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }//byte2hex()

    
    public static String getDigest(String input) {
        byte[] binput = input.getBytes();
        try {
            MessageDigest alg = MessageDigest.getInstance("MD5"); //or "SHA-1"
            alg.update(binput);
            byte[] digest = alg.digest();
            return byte2hex(digest);
        } catch(Exception e) {
        }
        return null;
    }


}
