package Mylist;

public class MyListmain {
    public static void main(String[] args) {
        IoMylist a = new IoMylist();
        a.add(123);
        a.add(456);
        a.add("abc");
        System.out.println(a);
        a.remove(456);
        System.out.println(a);
    }
}