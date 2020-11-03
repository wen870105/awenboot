package com.wen.awenboot.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * 调用PAAS集团接口加密处理
 */
@Slf4j
public class PaasSecretHandler {

    private final static String DATE_FORMAT = "EEE, dd MMM y HH:mm:ss 'GMT'";

    private final static String TIME_ZONE = "GMT";

    private final static String REF_PREFIX = "MIGU_";

    private static String secretId = "9Gm7422Kxq9Ff19cs9n7";

    private static String secret = "pxOzvqA+BTaOKguwaOaatOnpXWE9LL7LhRd5X4Ev1a/KtKI7ddYoxTN8GlI=";

    private static String algorithm = "HmacSHA256";

    public static void main(String[] args) throws Exception {
//        curl -H 'Content-Type:application/json' -H 'secretId:9Gm7422Kxq9Ff19cs9n7' -H 'requestRefId:MIGU_1604370186272' -H "x-date:Tue, 03 Nov 2020 02:23:06 GMT" -H 'signature:QgNZ3z7WAUDYEE80/iAU2BD9dUzmUlOjhDKcWoLRX/s=' -d '{"userId":"18703754147"}' http://10.191.1.48:19999/78578649/
        Map<String, String> map = getHeaderParams();
        System.out.println(JSON.toJSONString(map));

    }

    public static PaasBean getBean() throws Exception {
        PaasBean ret = new PaasBean();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        // GMT格式的时间
        String xdate = formatter.format(new Date());
        // 请求流水码，唯一标识
        String requestRefId = REF_PREFIX + System.currentTimeMillis();
        ret.setRequestRefId(requestRefId);
        ret.setSecretId(secretId);
        ret.setX_date(xdate);

        String signature = getSignature("requestRefId=" + requestRefId + "&secretId=" + secretId + "&x-date=" + xdate);
        ret.setSignature(signature);

        log.info("PaasBean={}", JSON.toJSONString(ret));
        return ret;
    }

    public static Map<String, String> getHeaderParams() throws Exception {
        Map<String, String> result = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        // GMT格式的时间
        String xdate = formatter.format(new Date());
        // 请求流水码，唯一标识
        String requestRefId = REF_PREFIX + System.currentTimeMillis();
        result.put("requestRefId", requestRefId);
        result.put("secretId", secretId);
        result.put("x-date", xdate);
        // 生成签名串
        String signature = getSignature("requestRefId=" + requestRefId + "&secretId=" + secretId + "&x-date=" + xdate);
        result.put("signature", signature);

        return result;
    }


    public static String getSignature(String message) throws Exception {
        String decodeSecret = base64Decode(secret);
        return doGetSignature(message, decodeSecret, algorithm);
    }

    private static String doGetSignature(String message, String secret, String hmacAlgorithm) throws Exception {
        Mac mac = Mac.getInstance(hmacAlgorithm);
        SecretKeySpec secretKeySpec;
        secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), hmacAlgorithm);
        mac.init(secretKeySpec);
        return new BASE64Encoder().encode(mac.doFinal(message.getBytes(StandardCharsets.UTF_8)));
    }

    private static String base64Decode(String secret) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(secret);
        return new String(b);
    }
}
