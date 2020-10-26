package com.migu.tsg.damportal.converter;

import com.migu.tsg.damportal.controller.response.TagOriginalQueryResponse;
import com.migu.tsg.damportal.domain.MiguTagAttribute;
import org.mapstruct.Mapper;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/25 23:41
 */
@Mapper(componentModel = "spring")
public interface MiguTagAttributeConverter {

    TagOriginalQueryResponse d2v(MiguTagAttribute obj);
}
