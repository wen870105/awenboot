/**
 * created by Wen.
 */
package com.wen.awenboot.domain;

import com.wen.awenboot.domain.base.BaseDomain;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Date;

/**
 * api接口调用次数表
 *
 * @author Wen
 * @since 2021-01-08
 */
@Data
public class MiguTagApiDetailCnt extends BaseDomain {
    //
    @Id
    private Long id;
    // api名称
    private String tagKey;
    // 调用次数
    private Long cnt;
    // 日期

    @Column(name = "create_date")
    private Date createDate;

}