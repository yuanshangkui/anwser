package newp;

/**
 * Created with IntelliJ IDEA.
 * User: YuanSK
 * Date: 2020/8/10 / 2:50
 * Description:
 */
public interface I1 {

    void f1();

    void f2();

    default void f3() {
        System.out.println("接口");
    }
}
