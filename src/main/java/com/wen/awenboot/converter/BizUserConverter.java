package com.wen.awenboot.converter;

import com.wen.awenboot.controller.request.AdduserRequest;
import com.wen.awenboot.domain.BizUser;
import com.wen.awenboot.vo.UserInfoVo;
import org.mapstruct.Mapper;

/**
 * @author wen
 * @version 1.0
 * @date 2021/2/7 14:34
 */
@Mapper(componentModel = "spring")
public interface BizUserConverter {
    BizUser d2v(AdduserRequest obj);

    UserInfoVo ud2v(BizUser obj);
}
