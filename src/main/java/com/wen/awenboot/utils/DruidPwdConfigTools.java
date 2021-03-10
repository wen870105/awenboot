package com.wen.awenboot.utils;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * @author wen
 * @version 1.0
 * @date 2021/3/7 19:34
 */
public class DruidPwdConfigTools {
    
    public static void main(String[] args) throws Exception {
        String pwd = args[0];
//        String pwd = "123456";
        generate(pwd);
        decrypt();
    }

    private static void generate(String pwd) throws Exception {
        System.out.println("加密密码,原串=" + pwd);
        String[] arr = {pwd};
        ConfigTools.main(arr);
    }

    private static void decrypt() throws Exception {
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJbWQidixP+AUswDAq3ptQn/3EN32cmaWktzW9hNsaJrYY5zYEsPylRGVgHQnHiHtic8oa1Qxy7VVavCNSjDjy0CAwEAAQ==";
        String pwd = "gBjpxyLLVOS3J1yfA+MExmAmC8wg2+3AJALoPcpP+gxTRG2GiBV9ujkQEdpqsRFBUZxAe61IG9SQcqtTpuD+Sg==";
        System.out.println("解密密码=" + pwd + ",publicKey=" + publicKey);
        String decrypt = ConfigTools.decrypt(publicKey, pwd);
        System.out.println(decrypt);
    }


}
