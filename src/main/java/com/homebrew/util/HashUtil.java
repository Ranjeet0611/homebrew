package com.homebrew.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class HashUtil {
    public static String getSHA265Hash(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        try{
            log.info("Start getSha265Hash");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            DigestInputStream digestInputStream = new DigestInputStream(inputStream,messageDigest);
            while(digestInputStream.read()!=-1){}
            byte[] hashBytes = messageDigest.digest();
            return String.format("%064x", new BigInteger(1, hashBytes));
        }
        catch (Exception e){
            log.error("Exception occurred while getSHA256Hash :{} :{}",e,e.getMessage());
            throw e;
        }
    }
}
