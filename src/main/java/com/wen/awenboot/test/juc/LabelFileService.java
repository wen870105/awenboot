package com.wen.awenboot.test.juc;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签数据写入到文件服务
 * 注意:只能单进程访问写入数据
 *
 * @author wen
 * @version 1.0
 * @date 2020/8/18 21:24
 */
public class LabelFileService {

    private static Logger logger = LoggerFactory.getLogger(LabelFileService.class);
    // 每个文件最大行数
    public static int LINE_SIZE = 20;

    // 建议单次写入不要过大
    public static int WRITE_SIZE = 10000;

    private static final byte[] SPLIT = {0x01};

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 生成的文件存放目录
     */
    private String fileDir;

    /**
     * 文件日期
     */
    private String fileDate = DateTime.now().toString("yyyyMMdd");

    /**
     * 数据文件,写入完成后分割此文件
     */
    private File dataFile = null;

    public LabelFileService(String labelName, String fileDir) {
        this.labelName = labelName;
        this.fileDir = fileDir;
        dataFile = buildTempDataFile();
    }

    /**
     * 数据写入到零时文件中,单次写入数据不能过大
     *
     * @param phoneList
     */
    public boolean writePhoneLog(List<String> phoneList) {
        if (phoneList == null || phoneList.size() <= 0) {
            logger.info("数据为空");
            return false;
        }
        if (phoneList.size() > WRITE_SIZE) {
            logger.info("写入数据过大,WRITE_SIZE={}", WRITE_SIZE);
            return false;
        }

        StringBuilder sb = new StringBuilder(100);
        for (String str : phoneList) {
            sb.append(str.trim()).append(new String(SPLIT)).append("1").append("\r\n");
        }

        try {
            FileUtils.write(dataFile, sb.toString(), "UTF-8", true);
        } catch (IOException e) {
            logger.error("写入临时文件失败,文件名称:{}", dataFile.getPath(), e);
        }

        return true;
    }

    public void splitFile() {
        // int 可以表示20+亿,我们数据不会超过这个数量级
        splitFile(dataFile, (int) lineCount(dataFile), LINE_SIZE);
    }

    /**
     * @Description: 按行分割文件
     * @Author: annecheng, 2019/7/11
     */
    private void splitFile(File file, int totalLine, int splitRow) {
        String fileName = file.getPath();
        logger.info("开始拆分文件,文件总行数totalLine={},LINE_SIZE={}", new Object[]{
                totalLine, LINE_SIZE});
        List<File> subFiles = new ArrayList<>();
        try {
            FileReader read = new FileReader(file);
            BufferedReader br = new BufferedReader(read);
            List<FileWriter> subFileList = new ArrayList<FileWriter>();
            int subFileSize = totalLine % splitRow == 0 ? totalLine / splitRow : totalLine / splitRow + 1;
            for (int i = 0; i < subFileSize; i++) {
                File temp = buildDataFile(i + 1);
                subFiles.add(temp);
                subFileList.add(new FileWriter(temp));
            }

            for (int rowNum = 1; rowNum <= totalLine; ++rowNum) {
                String row = br.readLine();
                int subFieldIndex = rowNum % splitRow == 0 ? rowNum / splitRow - 1 : rowNum / splitRow;
                subFileList.get(subFieldIndex).append(row + "\r\n");
            }

            for (int i = 0; i < subFileList.size(); i++) {
                subFileList.get(i).close();
            }
            br.close();
            file.delete();

            buildIndexFile(subFiles);
        } catch (Exception e) {
            logger.error("将大文件拆分成小文件异常，异常", e);
        }

    }


    /**
     * 创建索引文件
     *
     * @return
     */
    private File buildIndexFile(List<File> files) {
        // 格式: biz_0_DAY_20200814.CHK
        StringBuilder sb = new StringBuilder(50);
        sb.append(this.labelName).append("_0_DAY_");
        sb.append(this.fileDate);
        sb.append(".CHK");
        File indexFile = new File(this.fileDir + sb.toString());
        boolean flag = false;
        try {
            flag = indexFile.createNewFile();
            // 清空可能异常重复写入
            FileUtils.write(indexFile, "", "UTF-8");
            logger.info("创建索引文件:flag={},path={}", flag, indexFile.getPath());
        } catch (IOException e) {
            logger.info("创建索引文件失败,path={}", indexFile.getPath(), e);
        }


        // 追加当前文件到索引文件
        for (File f : files) {
            StringBuilder detail = new StringBuilder(50);
            detail.append(f.getName());
            detail.append(new String(SPLIT)).append(Long.toString(lineCount(f)));
            detail.append(new String(SPLIT)).append(this.fileDate);
            detail.append("\r\n");
            try {
                FileUtils.write(indexFile, detail.toString(), "UTF-8", true);
                logger.info("追加数据文件到索引中,name={}", f.getName());
            } catch (IOException e) {
                logger.error("追加索引文件失败", e);
            }
        }
        return indexFile;
    }


    private File buildTempDataFile() {
        StringBuilder sb = new StringBuilder(50);
        sb.append("temp_");
        sb.append(this.labelName).append("_0_DAY_").append(this.fileDate);
        sb.append(".DATA");
        File file = new File(this.fileDir + sb.toString());
        try {
            file.createNewFile();
        } catch (IOException e) {
            logger.error("创建临时文件失败,path={}", file.getPath(), e);
        }
        return file;
    }

    /**
     * 创建明细文件
     *
     * @return
     */
    private File buildDataFile(int fileIndex) {
        //格式:PRODUCT_0_DAY_20190918_01.DATA
        StringBuilder sb = new StringBuilder(50);
        sb.append(this.labelName).append("_0_DAY_").append(this.fileDate);
        sb.append("_0").append(fileIndex).append(".DATA");
        return new File(this.fileDir + sb.toString());
    }

    private long lineCount(File file) {
        try {
            return Files.lines(Paths.get(file.getPath())).count();
        } catch (Exception e) {
            logger.error("统计文件行数异常,file={}", file.getPath(), e);
            return 0;
        }
    }


    public static void main(String[] args) throws IOException {
        LabelFileService lsf = new LabelFileService("test", "D:\\");

        int total = 31;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            list.add(i + "");
        }

        lsf.writePhoneLog(list);
        lsf.writePhoneLog(list);
        lsf.writePhoneLog(list);
        lsf.splitFile();
    }

}
