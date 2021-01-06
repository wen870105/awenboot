package com.wen.awenboot.converter;

import com.wen.awenboot.cache.CntBean;
import org.mapstruct.Mapper;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/3 20:26
 */
@Mapper(componentModel = "spring")
public interface CntBeanConverter {
    CntBean clone(CntBean obj);
}
