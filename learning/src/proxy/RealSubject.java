package proxy;

/**
 * Created by jlgaoyuan on 2018/11/26.
 *
 */
public class RealSubject implements Subject {
    private String name = "byhieg";
    @Override
    public void visit() {
        System.out.println(name);
    }
}
