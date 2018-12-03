package proxy;

/**
 * Created by jlgaoyuan on 2018/11/26.
 *
 */
public class ProxySubject implements Subject {
    private Subject subject;
    public ProxySubject(Subject subject) {
        this.subject = subject;
    }
    @Override
    public void visit() {
        subject.visit();
    }
}
