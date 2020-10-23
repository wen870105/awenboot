//package com.wen.awenboot.biz.hbase;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.hbase.Cell;
//import org.apache.hadoop.hbase.CellUtil;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.apache.hadoop.hbase.client.Get;
//import org.apache.hadoop.hbase.client.HTable;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.security.UserGroupInformation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.security.auth.login.LoginContext;
//import java.security.PrivilegedAction;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author wen
// * @version 1.0
// * @date 2020/9/27 19:14
// */
//public class Main {
//    private static Logger logger = LoggerFactory.getLogger(Main.class);
//    private static Object lock = new Object();
//
//
//    private static LoginContext lc = null;
//    private static Connection connection = null;
//
//    /**
//     * 根据RowKey获取数据
//     *
//     * @param tableName 表名称
//     * @param rowKey    RowKey名称
//     */
//    public static Result queryByRowKey(String tableName, String rowKey) {
//        logger.debug("queryDataByRowKey start...");
//        HTable table = null;
//        for (int i = 0; i < 3; i++) {
//            try {
//                table = (HTable) getConnection(false).getTable(TableName.valueOf(tableName));
//                Get get = new Get(rowKey.getBytes());
//                return table.get(get);
//            } catch (Throwable ex) {
//                logger.error("get data from hbase error:" + ex.getMessage(), ex);
//                getConnection(true);
//            } finally {
//                ColseUtil.close(table);
//            }
//        }
//        return null;
//    }
//
//    private static Connection getConnection(boolean isforce) {
//
//        synchronized (lock) {
//            if (null == connection || connection.isClosed()) {
//                connect();
//            }
//            return connection;
//        }
//    }
//
//    private static Map<String, String> transToMap(Result ret) {
//        Map<String, String> map = new HashMap<>();
//        Cell[] cells = ret.rawCells();
//        if (cells.length > 0) {
//            for (Cell cell : cells) {
//                String key = new String(CellUtil.cloneQualifier(cell));
//                String val = new String(CellUtil.cloneValue(cell));
//                map.put(key, val);
//            }
//        }
//        return map;
//    }
//
//    public static void connect() {
//        logger.info("connect hbase start...");
//
//        logger.info("tettttttttttt");
//
//
//        try {
//            //        Configuration configuration = HBaseConfiguration.create();
//
////        configuration.set("hbase.zookeeper.quorum", "127.0.0.1");
////            connection = ConnectionFactory.createConnection(configuration);
//            final Configuration configuration = new Configuration(false);
//
//            configuration.addResource(new Path("D:\\hbase-conf\\hbase-site.xml"));
//            configuration.setInt("timeout", 120000);
//            configuration.setInt("hbase.client.retries.number", 1);
//            configuration.setInt("hbase.rpc.timeout", 60000);
//            configuration.setInt("hbase.client.operation.timeout", 300000);
//            configuration.setInt("hbase.client.scanner.timeout.period", 600000);
//
//
//            UserGroupInformation ugi = UserGroupInformation.getLoginUser();
//            connection = ugi.doAs(new PrivilegedAction<Connection>() {
//
//                @Override
//                public Connection run() {
//                    Connection _conn = null;
//                    try {
//                        _conn = ConnectionFactory.createConnection(configuration);
//                    } catch (Exception ex) {
//                        logger.error("connect hbase failed", ex);
//                        throw new RuntimeException("connect hbase failed", ex);
//                    }
//                    return _conn;
//                }
//            });
//            logger.info("connect hbase success!");
//        } catch (Exception ex) {
//            logger.error("connect hbase failed", ex);
//            throw new RuntimeException("connect hbase failed", ex);
//        }
//    }
//
//    public static void main(String[] args) {
////        try {
////            Class<?> classRef = Class.forName("sun.security.krb5.Config");
////            logger.info("====+{}", classRef);
////            System.out.println(classRef.toString());
////
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
//
//        logger.info("===============");
//
//        Result result = queryByRowKey("miners:migulabel_recommend", "1838279394");
//        Map<String, String> map = transToMap(result);
//        System.out.println(map);
//    }
//
//}
