/**
 * created by Wen.
 */
package com.wen.awenboot.domain;

import com.wen.awenboot.domain.base.BaseDomain;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author Wen
 * @since 2020-10-23
 */
@Data
public class ColumnValueMapBak extends BaseDomain {
    //
    @Id
    private Long id;

    private String columnNum;
    // 名称
    private String stringVal;
    private Date createTime;
    //
    private Date updateTime;

}