package com.wen.awenboot.integration.imei;

/**
 * @author wen
 * @version 1.0
 * @date 2021/4/6 16:03
 */
public class BaseResponseDTO {

    /**
     * head : {"requestRefId":"SJSREQ_201601010809108632A","responseCode":"0000","responseMsg":"查询成功"}
     * response : xkGUplbEe1OJUuFHlZnpaot1mILXteGgA+aHxhq0VAs=
     */

    private ResponseHeadDTO head;
    private String response;

    public ResponseHeadDTO getHead() {
        return head;
    }

    public void setHead(ResponseHeadDTO head) {
        this.head = head;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


}