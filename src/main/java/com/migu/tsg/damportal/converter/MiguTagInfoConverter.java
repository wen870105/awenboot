package com.migu.tsg.damportal.converter;

import com.migu.tsg.damportal.controller.request.TagCreateRequest;
import com.migu.tsg.damportal.controller.request.TagQueryRequest;
import com.migu.tsg.damportal.controller.request.TagUpdateRequest;
import com.migu.tsg.damportal.controller.response.TagDetailResponse;
import com.migu.tsg.damportal.controller.response.TagQueryResponse;
import com.migu.tsg.damportal.domain.MiguTagInfo;
import com.migu.tsg.damportal.domain.MiguTagInfoQuery;
import com.migu.tsg.damportal.domain.base.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/25 23:41
 */
@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface MiguTagInfoConverter {

    MiguTagInfo d2v(TagCreateRequest obj);


    @Mappings({
            @Mapping(target = "tagCode", source = "id"),
            @Mapping(target = "parentCode", source = "tagFather")
    })
    TagDetailResponse v2d(MiguTagInfo obj);

    @Mappings({
            @Mapping(target = "id", source = "tagCode")
    })
    MiguTagInfo d2v(TagUpdateRequest obj);

    MiguTagInfoQuery d2d(TagQueryRequest obj);

    Page<TagQueryResponse> p2p(Page<MiguTagInfo> obj);

    TagQueryResponse d2d(MiguTagInfo obj);
}
