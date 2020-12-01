package com.wen.awenboot.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2020/12/1 17:19
 */
@Slf4j
public class TagReaderUtils {
    private static final List<String> OP_LIST = Arrays.asList("等于", "大于", "小于", "介于");

    /**
     * 返回解析字符串中的标签
     *
     * @param param
     * @return
     */
    public static List<TagKvBean> readString(String param) {
        List<TagKvBean> ret = new ArrayList<>();
        String[] split = StrUtil.split(param, "\\n\\t");
        for (String str : split) {
            TagKvBean bean = resolveString(str);
            if (bean != null) {
                ret.add(bean);
            }
        }
        return ret;
    }

    public static TagKvBean resolveString(String param) {
        for (String op : OP_LIST) {
            if (StrUtil.contains(param, op)) {
                String[] split = StrUtil.split(param, op);
                if (split.length > 1) {
                    TagKvBean ret = new TagKvBean();
                    ret.setName(StrUtil.trim(split[0]));
                    ret.setVal(StrUtil.trim(split[1]));
                    ret.setOp(op);
                    return ret;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String s1 = "和（符合全部条件）\\n\\t移动集团.5G套餐标签个人版用户 等于 是.\\n\\t移动集团.是否咪咕音乐竞品用户 等于 是.\\n\\t移动集团.年龄 介于 35 和40";
        String s2 = "和（符合全部条件）\\n\\t 当 APP名称 等于 书旗小说 ,移动集团.APP是否活跃 等于 是.\\n\\t移动集团.年龄 大于 18.\\n\\t移动集团.性别 等于 女.\\n\\t移动集团.年龄 小于 26";
        List<TagKvBean> list1 = readString(s1);
        log.info("size={}", list1.size());
        log.info(list1.toString());
        log.info("===========");
        log.info("===========");
        log.info("===========");
        log.info("===========");
        log.info(JSON.toJSONString(readString(s2)));

    }
}
