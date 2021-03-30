package newp;

/**
 * Created with IntelliJ IDEA.
 * User: YuanSK
 * Date: 2020/8/10 / 2:51
 * Description:
 */
public class I1Impl implements I1{
    @Override
    public void f1() {

    }

    @Override
    public void f2() {

    }

    @Override
    public void f3() {
        System.out.println("实现类");
    }

    public void f4() {
        System.out.println("weqewq");
    }
}

class A{
    public static void main(String[] args) {
        I1 i1 = new I1Impl();
        i1.f3();
    }
}
