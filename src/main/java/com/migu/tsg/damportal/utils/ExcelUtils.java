package com.migu.tsg.damportal.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.migu.tsg.damportal.domain.MiguTagInfo;
import com.migu.tsg.damportal.enums.TagStatusEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/22 10:01
 */
public class ExcelUtils {
    public static final String SYSTEM = "system";

    public static void main(String[] args) {
        String s = "(tha)";
        System.out.println(getId(s, true));

        String filePath = "d:/test_" + DateTime.now().toString("yyyyMMddHHmmss") + ".xlsx";
        List<ObjEntry> list0 = readFile(0);
        List<ObjEntry> list1 = readFile(1);
        List<ObjEntry> list2 = readFile(2);
        List<ObjEntry> list3 = readFile(3);
        List<ObjEntry> list4 = readFile(4);
        writeTestFile(filePath, list0, "基础属性");
        writeTestFile(filePath, list1, "消费属性");
        writeTestFile(filePath, list2, "行为属性");
        writeTestFile(filePath, list3, "运营属性");
        writeTestFile(filePath, list4, "专区属性");
        System.out.println("end" + filePath);
    }

    public static String writeFile(String filePath, List<ExcelTagBean> list, String sheetName) {
        ExcelWriter writer = ExcelUtil.getWriter(filePath, sheetName);
        // 生成文件内容顺序和下面有关,不要删除
        writer.addHeaderAlias("category1", "一级分类");
        writer.addHeaderAlias("category2", "二级分类");
        writer.addHeaderAlias("category3", "三级分类");
        writer.addHeaderAlias("category4", "四级分类");
        writer.addHeaderAlias("category5", "五级分类");
        writer.addHeaderAlias("category6", "六级分类");
        writer.addHeaderAlias("tagVal", "标签值");
        writer.addHeaderAlias("datasourceMemo", "数据源");
        writer.addHeaderAlias("generateRule", "计算规则");
        writer.addHeaderAlias("updatePeriod", "更新周期");
        writer.addHeaderAlias("updateTime", "最新更新时间");
//        writer.addHeaderAlias("更新及时率", "更新及时率");
        writer.addHeaderAlias("creator", "创建人");
//        writer.addHeaderAlias("标签覆盖人数", "标签覆盖人数");
//        writer.addHeaderAlias("标签覆盖率", "标签覆盖率");
        writer.addHeaderAlias("workflowMemo", "工作流");
        writer.addHeaderAlias("categoryType", "标签分类");
        writer.addHeaderAlias("tagType", "标签类型");
        writer.setOnlyAlias(true);

        List<ExcelTagBean> rows = CollUtil.newArrayList(list.stream().filter(p -> StrUtil.isNotBlank(p.getTagType())).collect(Collectors.toList()));

        writer.write(rows, true);
        writer.close();
        return filePath;

    }


    public static String writeTestFile(String filePath, List<ObjEntry> list, String sheetName) {


        ExcelWriter writer = ExcelUtil.getWriter(filePath, sheetName);
        // 生成文件内容顺序和下面有关,不要删除
        writer.addHeaderAlias("一级分类", "一级分类");
        writer.addHeaderAlias("二级分类", "二级分类");
        writer.addHeaderAlias("三级分类", "三级分类");
        writer.addHeaderAlias("四级分类", "四级分类");
        writer.addHeaderAlias("五级分类", "五级分类");
        writer.addHeaderAlias("六级分类", "六级分类");
        writer.addHeaderAlias("标签值", "标签值");
        writer.addHeaderAlias("数据源", "数据源");
        writer.addHeaderAlias("计算规则", "计算规则");
        writer.addHeaderAlias("更新周期", "更新周期");
        writer.addHeaderAlias("最新更新时间", "最新更新时间");
        writer.addHeaderAlias("更新及时率", "更新及时率");
        writer.addHeaderAlias("创建人", "创建人");
        writer.addHeaderAlias("标签覆盖人数", "标签覆盖人数");
        writer.addHeaderAlias("标签覆盖率", "标签覆盖率");
        writer.addHeaderAlias("工作流", "工作流");
        writer.addHeaderAlias("标签分类", "标签分类");
        writer.addHeaderAlias("标签类型", "标签类型");

        List<ObjEntry> rows = CollUtil.newArrayList(list);

        writer.write(rows, true);
        writer.close();
        return filePath;

    }

    public static List<ObjEntry> readFile(int sheetIndex) {
        ExcelReader reader = ExcelUtil.getReader("d:/import_test.xlsx", sheetIndex);
        List<ObjEntry> list2 = reader.readAll(ObjEntry.class);
        reader.close();
        return list2;
    }

    public static MiguTagInfo buildMiguTagInfo(ObjEntry entry, Map<String, MiguTagInfo> fathersMap) {

        Date date = new Date();
        MiguTagInfo obj = new MiguTagInfo();
        obj.setId(getId(entry));
        obj.setTagName(getTagName(entry));
        obj.setTagClass(getTagClass(entry));
        obj.setTagType(getTagType(entry.get标签类型()));
        obj.setTagFather(getTagFather(entry));
        obj.setTagValue(entry.get标签值());
        obj.setCategoryType(getCategroy(entry.get标签分类()));
//        obj.setMemo();
        obj.setWorkflowMemo(entry.get工作流());
        obj.setDatasourceMemo(entry.get数据源());
        obj.setGenerateRule(entry.get计算规则());
        obj.setUpdatePeriod(getUpdatePeriod(entry.get更新周期()));
//        obj.setUpdatePeriodVal();
        obj.setCreateTime(date);
        obj.setUpdateTime(date);
        obj.setStatus(TagStatusEnum.OPEN.getCode());
        obj.setCreatorAccount(SYSTEM);
        obj.setCreator(SYSTEM);

        Integer tagClass = obj.getTagClass();
        setFather(entry, fathersMap, tagClass);

        return obj;
    }

    private static void setFather(ObjEntry entry, Map<String, MiguTagInfo> fathersMap, Integer tagClass) {
        Date date = new Date();
        String node5 = entry.get五级分类();
        String node4 = entry.get四级分类();
        String node3 = entry.get三级分类();
        String node2 = entry.get二级分类();
        String node1 = entry.get一级分类();

        switch (tagClass) {
            case 6:
                String tagId5 = getId(node5, false);
                MiguTagInfo m5 = new MiguTagInfo();
                m5.setId(tagId5);
                m5.setTagName(getTagName(node5));
                m5.setTagClass(5);
                m5.setTagFather(getId(node4, false));
                m5.setCreateTime(date);
                m5.setUpdateTime(date);
                m5.setCreator(SYSTEM);
                m5.setCreatorAccount(SYSTEM);
                fathersMap.putIfAbsent(tagId5, m5);

            case 5:
                String tagId4 = getId(node4, false);
                MiguTagInfo m4 = new MiguTagInfo();
                m4.setId(tagId4);
                m4.setTagName(getTagName(node4));
                m4.setTagClass(4);
                m4.setTagFather(getId(node3, false));
                m4.setCreateTime(date);
                m4.setUpdateTime(date);
                m4.setCreator(SYSTEM);
                m4.setCreatorAccount(SYSTEM);
                fathersMap.putIfAbsent(tagId4, m4);
            case 4:
                String tagId3 = getId(node3, false);
                MiguTagInfo m3 = new MiguTagInfo();
                m3.setId(tagId3);
                m3.setTagName(getTagName(node3));
                m3.setTagClass(3);
                m3.setTagFather(getId(node2, false));
                m3.setCreateTime(date);
                m3.setUpdateTime(date);
                m3.setCreator(SYSTEM);
                m3.setCreatorAccount(SYSTEM);
                fathersMap.putIfAbsent(tagId3, m3);
            case 3:
                String tagId2 = getId(node2, false);
                MiguTagInfo m2 = new MiguTagInfo();
                m2.setId(tagId2);
                m2.setTagName(getTagName(node2));
                m2.setTagClass(2);
                m2.setTagFather(getId(node1, false));
                m2.setCreateTime(date);
                m2.setUpdateTime(date);
                m2.setCreator(SYSTEM);
                m2.setCreatorAccount(SYSTEM);
                fathersMap.putIfAbsent(tagId2, m2);
            case 2:
                String tagId1 = getId(node1, false);
                MiguTagInfo m1 = new MiguTagInfo();
                m1.setId(tagId1);
                m1.setTagName(getTagName(node1));
                m1.setTagClass(1);
                m1.setCreator(SYSTEM);
                m1.setCreatorAccount(SYSTEM);
                m1.setCreateTime(date);
                m1.setUpdateTime(date);
                fathersMap.putIfAbsent(tagId1, m1);
        }
    }

    private static Integer getTagType(String str) {
        // 1枚举2字符串3数值
        if (str.contains("enum") || str.contains("枚举")) {
            return 1;
        } else if (str.contains("value")) {
            return 2;
        } else if (str.contains("number")) {
            return 3;
        }
        return null;
    }

    private static Integer getUpdatePeriod(String str) {
        // 1天,2周,3月,4不定期
        if (str.contains("天")) {
            return 1;
        } else if (str.contains("周")) {
            return 2;
        } else if (str.contains("月")) {
            return 3;
        } else if (str.contains("不定期")) {
            return 4;
        }
        return null;
    }

    private static String getTagFatherContent(ObjEntry entry) {
        int tagClass = getTagClass(entry);
        if (tagClass != 1) {
            if (getTagClass(entry) == 6) {
                return entry.get五级分类();
            }

            if (getTagClass(entry) == 5) {
                return entry.get四级分类();
            }
            if (getTagClass(entry) == 4) {
                return entry.get三级分类();
            }
            if (getTagClass(entry) == 3) {
                return entry.get二级分类();
            }
            if (getTagClass(entry) == 2) {
                return entry.get一级分类();
            }
        }
        return null;
    }

    private static String getTagFather(ObjEntry entry) {
        String cont = getTagFatherContent(entry);
        return getId(cont, false);
    }

    private static String getTagName(ObjEntry entry) {
        String cont = getIdContent(entry);
        return getTagName(cont);
    }

    private static String getTagName(String cont) {
        String id = getId(cont, true);
        // 替换掉ID
        return cont.replace(id, "");
    }

    private static String getId(ObjEntry entry) {
        String cont = getIdContent(entry);
        return getId(cont, false);
    }

    /**
     * 获取id,
     *
     * @param str
     * @param markFlag 是否返回带括号
     * @return
     */
    public static String getId(String str, boolean markFlag) {
        String s1 = ReUtil.get("\\(.*\\)", str, 0);
        if (markFlag) {
            return s1;
        }
        if (StrUtil.isNotBlank(s1)) {
            return StrUtil.sub(StrUtil.sub(s1, 1, s1.length()), 0, -1);
        }
        return s1;
    }


    private static String getIdContent(ObjEntry entry) {
        if (getTagClass(entry) == 6) {
            return entry.get六级分类();
        }

        if (getTagClass(entry) == 5) {
            return entry.get五级分类();
        }
        if (getTagClass(entry) == 4) {
            return entry.get四级分类();
        }
        if (getTagClass(entry) == 3) {
            return entry.get三级分类();
        }
        if (getTagClass(entry) == 2) {
            return entry.get二级分类();
        }
        return entry.get一级分类();
    }

    private static int getTagClass(ObjEntry entry) {
        String c6 = entry.get六级分类();
        String c5 = entry.get五级分类();
        String c4 = entry.get四级分类();
        String c3 = entry.get三级分类();
        String c2 = entry.get二级分类();
        String c1 = entry.get一级分类();
        if (StrUtil.isNotBlank(c6)) {
            return 6;
        }
        if (StrUtil.isNotBlank(c5)) {
            return 5;
        }
        if (StrUtil.isNotBlank(c4)) {
            return 4;
        }
        if (StrUtil.isNotBlank(c3)) {
            return 3;
        }
        if (StrUtil.isNotBlank(c2)) {
            return 2;
        }
        return 1;
    }

    //标签分类:1统计2规则3挖掘
    private static Integer getCategroy(String str) {
        if ("统计".equalsIgnoreCase(str)) {
            return 1;
        }
        if ("规则".equalsIgnoreCase(str)) {
            return 2;
        }
        if ("挖掘".equalsIgnoreCase(str)) {
            return 3;
        }
        return null;
    }


    @Data
    public static class ObjEntry {
        private String 一级分类;
        private String 二级分类;
        private String 三级分类;
        private String 四级分类;
        private String 五级分类;
        private String 六级分类;
        private String 标签值;
        private String 数据源;
        private String 计算规则;
        private String 更新周期;
        private String 最新更新时间;
        private String 更新及时率;
        private String 创建人;
        private String 标签覆盖人数;
        private String 标签覆盖率;
        private String 工作流;
        private String 标签分类;
        private String 标签类型;
    }
}
