package com.wen.awenboot.integration.zhuangku;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/2 16:46
 */
@Data
public class ResultMusic {

    private Head head;
    private Response response;

    public static void main(String[] args) {
        JSONObject resp = new JSONObject();

        JSONObject head = new JSONObject();
        head.put("responseCode", "00000");

        JSONObject response = new JSONObject();


        JSONArray arr = new JSONArray();
        JSONObject obj1 = new JSONObject();
        obj1.put("product_info", "1111");
        arr.add(obj1);

        response.put("param", arr);
        resp.put("head", head);
        resp.put("response", response);

        System.out.println(resp.toJSONString());

        ResultMusic resultMusic = JSON.parseObject(resp.toJSONString(), ResultMusic.class);
        System.out.println(JSON.toJSONString(resultMusic));
    }
}
