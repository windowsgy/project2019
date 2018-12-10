package runnable;

/**
 * Created by jlgaoyuan on 2018/12/10.
 * 静态代理
 */
public class StaticProxy {
    public static void main(String[] args) {

        You you = new You();
        WeddingCompany weddingCompany = new WeddingCompany(you);
        weddingCompany.marry();

    }

}

interface Marry{
    void marry();
}

class You implements Marry{
    @Override
    public void marry() {
        System.out.println("you and me:");

    }
}

class WeddingCompany implements Marry{
    private Marry you;

    private void before(){
        System.out.println("start");

    }

    private void after(){
        System.out.println("end");

    }


    WeddingCompany(Marry you){
        this.you = you;
    }

    @Override
    public void marry() {
        before();
        you.marry();
        after();
    }
}





