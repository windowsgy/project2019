package mail;


import java.util.Vector;

/**
 *
 * Created by jlgaoyuan on 2018/5/7.
 */
public class StruMail {

    private String to; // 收件人
    private String from; // 发件人
    private String host; // SMTP主机
    private String username; // 发件人的用户名
    private String password; // 发件人的密码
    private String subject; // 邮件主题
    private String content; // 邮件正文
    private Vector<String> file; // 多个附件
    private String fileName; // 附件的文件名

    public String getTo() {
        return to;
    }

    public void setTo(String str) { this.to = str;}

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() { return fileName; }

    public void setFilename(String filename) {
        this.fileName = filename;
    }

    public Vector<String> getFile() {
        return file;
    }

    public void attachFile(String fileName) {
        if (file == null){
            file = new Vector<String>();
        }
        file.addElement(fileName);
    }
}
