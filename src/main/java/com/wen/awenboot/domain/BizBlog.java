/**
 * created by Wen.
 */
package com.wen.awenboot.domain;

import com.wen.awenboot.domain.base.BaseDomain;

import javax.persistence.Id;
import java.util.Date;

/**
 * 课程内容
 * @author Wen
 * @since 2021-02-05
 */
public class BizBlog extends BaseDomain{
	//
	@Id
	private Integer id;
	// 课程名称
	private String title;
	// 头像图片
	private String thumbnail;
	// 视频地址
	private String video;
	// ppt地址
	private String ppt;
	// 
	private Date createDate;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getVideo() {
		return video;
	}

	public void setPpt(String ppt) {
		this.ppt = ppt;
	}

	public String getPpt() {
		return ppt;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return createDate;
	}
}