package com.migu.tsg.damportal.converter;

import com.migu.tsg.damportal.cache.TagDetailCntVO;
import com.migu.tsg.damportal.domain.MiguTagDetailCnt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/23 16:36
 */
@Mapper(componentModel = "spring")
public interface MiguTagDetailCntConverter {

    @Mappings({
            @Mapping(target = "tagCnt", ignore = true)
    })
    MiguTagDetailCnt v2d(TagDetailCntVO obj);

    TagDetailCntVO clone(TagDetailCntVO obj);

}
