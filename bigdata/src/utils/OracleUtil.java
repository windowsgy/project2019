package utils;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * Oracle查询工具
 */


public class OracleUtil {

    private static Connection con;// 获取连接
    private static PreparedStatement pre;//
    private static ResultSet rs; //结果集

    public OracleUtil(String url, String username, String password) {

        try {
            //   log.info("加载ORACLE数据库驱动");
            Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
        } catch (ClassNotFoundException e) {
            Log.error("加载ORACLE数据库驱动失败！" + e.getMessage());
        }
        try {
            //  log.info("开始连接数据库！");
            con = DriverManager.getConnection(url, username, password);// 获取连接
            //  log.info("连接数据库成功！");
        } catch (Exception e) {
            Log.error("连接数据库失败！" + e.getMessage());
        }
    }

    /**
     * 查询数据库
     *
     * @param sql 查询语句
     * @return rs
     */

    public ResultSet select(String sql) {
        //   log.linel4();
        try {
            //   log.info("执行 select 语句 :" + sql);
            pre = con.prepareStatement(sql);// 实例化预编译语句
            rs = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
        } catch (Exception e) {
            Log.error("查询记录失败 :" + e.getMessage());
            return null;
        }
        return rs;
    }

    /**
     * 查询数据库
     *
     * @param sql 查询语句
     * @return rs
     */

    public boolean update(String sql) {
        try {
            pre = con.prepareStatement(sql);// 实例化预编译语句
            pre.executeUpdate();//更新
        } catch (Exception e) {
            Log.error("查询记录失败 :" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除数据
     *
     * @param sql SQL语句
     * @return rs
     */
    public boolean delete(String sql) {
        try {
            //   log.info("执行 delete 语句 :" + sql);
            pre = con.prepareStatement(sql);// 实例化预编译语句
            pre.executeUpdate();// 执行查询，注意括号中不需要再加参数
        } catch (Exception e) {
            Log.error("删除记录失败 :" + e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * 关闭数据库连接
     */
    public void closeCon() {
        //     log.linel4();
        try {
            // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
            // 注意关闭的顺序，最后使用的最先关闭
            if (rs != null)
                rs.close();
            if (pre != null)
                pre.close();
            if (con != null)
                con.close();
            //       log.info("数据库连接已关闭！");
        } catch (Exception e) {
            Log.error("数据库关闭错误" + e.getMessage());
        }
    }


    public PreparedStatement insert(String sql) {
        //     log.linel4();
        try {
            //   log.info("执行预编译:" + sql);
            pre = con.prepareStatement(sql);
        } catch (Exception e) {
            Log.error("获取失败:" + e.getMessage());
            return null;
        }
        return pre;
    }
}
