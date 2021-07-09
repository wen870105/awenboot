package com.wen.awenboot.integration.imei;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.wen.awenboot.utils.ImeiUtils;
import com.wen.awenboot.utils.SM4Util;

/**
 * @author wen
 * @version 1.0
 * @date 2021/6/23 15:30
 */
public class Main {
    public static void main(String[] args) throws Exception {


        ReqBodyDTO body = new ReqBodyDTO();
        body.setImei("86297603333084");

        ReqParamDTO param = new ReqParamDTO();
        param.setParam(body);
        String json = JSON.toJSONString(param);
        System.out.println(json);

        String param1 = ImeiUtils.getParam(json);

        ImeiRequestDTO req = new ImeiRequestDTO();
        ReqHeadDTO reqHeadDTO = new ReqHeadDTO();
        reqHeadDTO.setRequestRefId(IdUtil.simpleUUID());
        reqHeadDTO.setSecretId("10118");
        reqHeadDTO.setSignature(ImeiUtils.getSignature(reqHeadDTO));
        req.setHead(reqHeadDTO);
        req.setRequest(param1);

        System.out.println("decode=" + SM4Util.decode(param1, ImeiUtils.secretKey));


        System.out.println(JSON.toJSONString(req));

        System.out.println("解密前:b03a95e9c8856d96f18d4d47a5e3f8cc8fbd31a085e9da8c49a4527b73f2e1d6");
        System.out.println("resp=" + SM4Util.decode("b03a95e9c8856d96f18d4d47a5e3f8cc8fbd31a085e9da8c49a4527b73f2e1d6", ImeiUtils.secretKey));

    }
}
