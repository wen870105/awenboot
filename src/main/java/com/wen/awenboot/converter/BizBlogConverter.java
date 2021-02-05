package com.wen.awenboot.converter;

import com.wen.awenboot.controller.response.DetailResponse;
import com.wen.awenboot.domain.BizBlog;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/3 20:26
 */
@Mapper(componentModel = "spring")
public interface BizBlogConverter {
    DetailResponse blog2Detail(BizBlog obj);

    List<DetailResponse> blogList2Detail(List<BizBlog> obj);
}
