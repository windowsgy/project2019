package model.plant;

/**
 * Created by jlgaoyuan on 2018/11/14.
 *
 */
public class Main {
    public static void main(String[] args) {
        SendFactory factory = new SendFactory();
        Sender sender = factory.produce("sms");
        sender.send();
    }
}
