package com.wen.awenboot.controller.response;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/23 21:46
 */
@Data
public class DetailResponse {
    private String id;
    private String title;
    private String thumbnail;
    private String video;
    private String ppt;
    private boolean paid;
}
