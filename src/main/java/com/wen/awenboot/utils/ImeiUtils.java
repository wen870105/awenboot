package com.wen.awenboot.utils;

import com.wen.awenboot.integration.imei.ReqHeadDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wen
 * @version 1.0
 * @date 2021/3/3 11:29
 */
@Slf4j
public class ImeiUtils {

    public static String secretId = "10118";

    //    public static String secretKey = "Au2x3tCGRNqd62cd98okQVCN1Cozk2pC";
    public static String secretKey = "MFZxY29UNjkxQ204OVgycXh1dDE3Nll1";

    public static void main(String[] args) {
//        ReqHeadDTO head = new ReqHeadDTO();
//        head.setRequestRefId("SJSREQ_201601010809108632A");
//        head.setSecretId("KFZQpn74WFkmLPx3gnP");
//        head.setSignature(getSignature(head));
//        System.out.println(head.getSignature());

//        SM3Util.getSignatureBySM3("requestRefId=SJSREQ_201601010809108632A&secretId=KFZQpn74WFkmLPx3gnP",)
//        String imei = "860192000079419";
        System.out.println("A0000058841582".length());
        System.out.println(getImeiBy14("A100005A6916BB"));
        System.out.println(getImeiBy14("A0000058841582"));
//        System.out.println(getImeiBy14("86019200007941"));
//        System.out.println(getImeiBy14("864230039369399999"));

    }

    public static String decodeParam(String json) {
        try {
            return SM4Util.decode(json, secretKey);
        } catch (Exception e) {
            log.error("加密param参数错误,json={}", json, e);
        }
        return null;
    }

    public static String getParam(String json) {
        try {
            return SM4Util.encode(json, secretKey);
        } catch (Exception e) {
            log.error("加密param参数错误,json={}", json, e);
        }
        return null;
    }


    public static String getSignature(ReqHeadDTO head) {
        StringBuilder sb = new StringBuilder(80);
        sb.append("requestRefId=").append(head.getRequestRefId());
        sb.append("&secretId=").append(head.getSecretId());
        return SM3Util.getSignatureBySM3(sb.toString(), secretKey);
    }


    /**
     * 参考的老的代码
     * 通过imei的前14位获取完整的imei(15位)
     *
     * @param imeiString
     * @return
     */
    public static String getImeiBy14(String imeiString) {
        if (!StringUtils.isNumeric(imeiString) || imeiString.length() < 14) {
            return null;
        }
        String tempImei = imeiString;
        if (imeiString.length() > 14) {
            tempImei = tempImei.substring(0, 14);
        }
        String retVal = null;
        char[] chars = tempImei.toCharArray();
        int resultInt = 0;
        for (int i = 0; i < chars.length; i++) {
            int a = Integer.parseInt(String.valueOf(chars[i]));
            i++;
            int temp = Integer.parseInt(String.valueOf(chars[i])) * 2;
            int b = temp < 10 ? temp : temp - 9;
            resultInt += a + b;
        }
        resultInt %= 10;
        resultInt = resultInt == 0 ? 0 : 10 - resultInt;
        retVal = tempImei + resultInt;

        return retVal;
    }


}
