/**
 * created by Wen.
 */
package com.wen.awenboot.domain;

import com.wen.awenboot.domain.base.BaseDomain;
import lombok.Data;

import javax.persistence.Id;

/**
 * 课程内容
 *
 * @author Wen
 * @since 2021-02-05
 */
@Data
public class BizBlogVistorCounter extends BaseDomain {
    @Id
    private Integer id;
    // 课程名称
    private Integer blogId;
    // 计数
    private Integer cnt;

}