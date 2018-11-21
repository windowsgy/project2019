package model.plant;

/**
 * Created by jlgaoyuan on 2018/11/14.
 *
 */
public class MailSender implements Sender {
    @Override
    public void send() {
        System.out.println("this is mailSender");
    }
}
