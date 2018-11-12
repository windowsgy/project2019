package utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * HDFS 工具
 * Created by jlgaoyuan on 2017/8/22.
 */
public class HDFSUtils {

    private static Configuration conf = new Configuration();

    /**
     * 构造方法
     * @param map 传入构造参数
     */
    public HDFSUtils(Map<String,String> map) {
        String clusterURI = map.get("clusterURI");
        String clusterName = map.get("clustername");
        String nameNode1 = map.get("namenode1");
        String nameNode2 = map.get("namenode2");
        String namenode1URI =map.get("namenode1URI");
        String namenode2URI = map.get("namenode2URI");
        String keyFilePath = map.get("keyFilePath");
        String homeDir = map.get("homedir");
        String principal = map.get("principal");
        String namenodes = nameNode1 + "," + nameNode2;//拼接字符串
        String namenode1rpc = "dfs.namenode.rpc-address." + clusterName + "." + nameNode1;//拼接RPC
        String namenode2rpc = "dfs.namenode.rpc-address." + clusterName + "." + nameNode2;//拼接RPC
        conf.set("fs.defaultFS", clusterURI);
        conf.set("dfs.nameservices", clusterName);
        conf.set("dfs.ha.namenodes." + clusterName, namenodes);
        conf.set(namenode1rpc, namenode1URI);
        conf.set(namenode2rpc, namenode2URI);
        conf.set("dfs.client.failover.proxy.provider." + clusterName, "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");//Kerberos身份验证模块文件位置。
        System.setProperty("hadoop.home.dir", homeDir);//无实质意义，用于解决未在HADOOP环境下执行，需要设置此参数。避免提示错误找不到HADOOP_HOME。
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());//设置文件系统为HDFS
        //增加hadoop开启安全配置
        conf.setBoolean("hadoop.security.authorization", true);
        //配置安全认证方式为kerberos
        conf.set("hadoop.security.authentication", "Kerberos");
        //设置namenode的principal
        conf.set("dfs.namenode.kerberos.principal", principal);
        //设置datanode的principal
        conf.set("dfs.datanode.kerberos.principal", principal);
        //通过hadoop security下中的 UserGroupInformation类来实现使用keytab文件登录
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromKeytab(principal, keyFilePath);//设置登录的kerberos principal和对应的keytab文件，其中keytab文件需要kdc管理员生成给到开发人员
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * 获取HSFS目录下所有文件的路径
     * @param hdfsPath HDFS目录路径
     * @return list
     */
    public List<String> getFilesName(String hdfsPath) {
        Log.linel4();
        Log.info("获取目录中文件:" + hdfsPath);
        Path path = new Path(hdfsPath);
        List<String> list = new ArrayList<>();
        try {
            FileSystem fs = path.getFileSystem(conf);
            FileStatus[] fileStatus = fs.listStatus(path);
            for (FileStatus fileStatu : fileStatus) {
                list.add(fileStatu.getPath().toString());
            }
            fs.close();
        } catch (IOException e) {
           Log.error(e.getMessage());
        }
        Log.linel4();
        return list;
    }

    /**
     * 复制HDFS文件至本地
     *
     * @param hdfsPath      HDFS 文件路径
     * @param localFilePath 本地文件路径
     */
    public void copyHdfsToLocal(String hdfsPath, String localFilePath) {
        Log.linel4();
        Log.info("下载HDFS文件:");
        Log.info("源文件路径:" + hdfsPath);
        try {
            Path path = new Path(hdfsPath);
            FileSystem fs = FileSystem.get(new URI(hdfsPath), conf);
            if (!fs.exists(path)) {//如果文件不存在则继续
                Log.info("HDFS文件不存在:" + hdfsPath);
                return;
            }
            InputStream in = fs.open(path);
            OutputStream out = new FileOutputStream(localFilePath);
            IOUtils.copyBytes(in, out, 4096, true);
            Log.info("到本地" + localFilePath);
            fs.close();
        } catch (IOException | URISyntaxException e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * 字符串写入HDFS文件，如果文件不存在则创建文件
     * @param hdfsPath hdfs 文件路径
     */
    public void wr(String hdfsPath, String str) {
        Log.linel4();
        FileSystem fs;
        Path path = new Path(hdfsPath);
        try {
            fs = FileSystem.get(URI.create(hdfsPath), conf);
            if (!fs.exists(path)) {//如果文件不存在则创建文件
                Log.info("创建HDFS文件:" + hdfsPath);
                fs.create(path).close();
            }
            FSDataOutputStream out = fs.append(path); //定义输出流
            if (str != null) {
                //  out.writeBytes(str);
                Log.info("数据写入HDFS文件:" + hdfsPath);
                out.write(str.getBytes("UTF-8"));
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }
}
