package com.wss.demo.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class Decrypt {
    public static String encrypt(String name, String password){
        String algorithmName= Md5Hash.ALGORITHM_NAME;
        String credentials=password;
        String salt=name;
        int hashIterations=20;
        SimpleHash simpleHash=new SimpleHash(algorithmName,credentials,salt,hashIterations);
        return simpleHash.toString();
    }
}
