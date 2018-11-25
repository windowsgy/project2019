package utils.collect;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import base.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;


/**
 * Created by jlgaoyuan on 2017/6/16.
 * SFTP 客户端上传文件
 */
public class SFTP {

    private static String host;// 用户名,系统默认的账户名
    private static Integer port;// 你安装时选设置的密码
    private static String username;
    private static String password;


    public SFTP(Map<String, String> map) {
        host = map.get("ip");// 用户名,系统默认的账户名
        port = Integer.parseInt(map.get("port"));// 你安装时选设置的密码
        username = map.get("username");
        password = map.get("password");
    }

    private static ChannelSftp sftp = null;

    /**连接SFTP服务器，设置本地文件路径，远程服务器目录
     *
     * @param localFilePath 本地路径
     * @param remoteDir sftp目录
     * @return boolean
     */
    public static boolean sftpClient(String localFilePath, String remoteDir) {
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            Log.info("SFTP 建立连接");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect(20000);
            Log.info("SFTP 连接已经建立");
            Log.info("SFTP 打开通道.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect(20000);
            sftp = (ChannelSftp) channel;
            Log.info("SFTP 连接到主机 " + host);
            upload(remoteDir, localFilePath, sftp);
            sshSession.disconnect();
            Log.info("SFTP 上传完成");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            disconnect(sftp);
        }
    }


    /**
     * 上传文件
     *
     * @param directory 远程目录
     * @param localFile 本地路径
     * @param sftp      实例
     */
    private static void upload(String directory, String localFile, ChannelSftp sftp) {
        Log.info("上传文件");
        try {
            sftp.cd(directory);
            File file = new File(localFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * 断开链接
     * @param sftp 实例
     */
    private static void disconnect(ChannelSftp sftp) {
        Log.info("关闭SFTP");
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
                sftp.quit();
            } else if (sftp.isClosed()) {
                sftp.exit();
                Log.info("SFTP已经关闭");
            }
        }
    }

}
