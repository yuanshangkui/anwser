package MyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MyListMain {
    public static void main(String[] args) {

        Iterator<Object> iterator = new ArrayList<>().iterator();
        while (iterator.hasNext()) {
            System.out.println("iterator.next() = " + iterator.next());
        }

        MyList a = new MyListOfArrayList("abcd",1233,0.1323,null);
        Object o = a;
//        MyList a = new MyListOfArrayList();
        System.out.println("a = " + a);
        a.add(true);
        System.out.println("a = " + a);
        a.addAll(new Object[]{"ccc",'c'});
        System.out.println("a = " + a);
        a.remove(0.1323);
        System.out.println("a = " + a);
        a.removeAll(new Object[]{"ccc",'c'});
        System.out.println("a = " + a);
        a.set(0,"0000");
        System.out.println("a = " + a);
        System.out.println("a.contains(1233) = " + a.contains(1233));
        System.out.println("a.contains(-1) = " + a.contains(-1));
        System.out.println("a.containsAll(new Object[]{null,true}) = " + a.containsAll(new Object[]{null, true}));
        System.out.println("a.containsAll(new Object[]{-1,\"0000\"}) = " + a.containsAll(new Object[]{-1, "0000"}));
        System.out.println("a.get(0) = " + a.get(0));
        a.add("0000");
        System.out.println("a = " + a);
        System.out.println("a.indexOf(\"0000\") = " + a.indexOf("0000"));
        System.out.println("a.indexOf(\"0\") = " + a.indexOf("0"));
        System.out.println("a.lastIndexOf(\"0000\") = " + a.lastIndexOf("0000"));
        System.out.println("a.lastIndexOf(\"0\") = " + a.lastIndexOf("0"));
        System.out.println("a.isEmpty() = " + a.isEmpty());
        System.out.println("a.size() = " + a.size());
        a.clear();
        System.out.println("a.isEmpty() = " + a.isEmpty());
        System.out.println("a.size() = " + a.size());

        a.add("element");
        Object[] objects = a.toArray();
        System.out.println("a = " + a);
        System.out.println("objects = " + Arrays.toString(objects));
        objects[0] = "abc";
        System.out.println("a = " + a);
        System.out.println("objects = " + Arrays.toString(objects));

        MyListOfArrayList list = new MyListOfArrayList("element");
        System.out.println("a.equals(list) = " + a.equals(list));
        list.add("1231");
        System.out.println("list.equals(a) = " + list.equals(a));

        /*

        System.out.println(a.toString());
        System.out.println(a.add("123"));
        a.add(12133);
        System.out.println(a.toString());
        System.out.println(a.getSize());
//        System.out.println(a.get(-1));
        System.out.println(a.get(3));
        System.out.println(a.isEmpty());
        Object[] object = new Object[]{666,"wsm"};
        System.out.println(a.addAll(object));
        System.out.println(a.toString());
        System.out.println(a.contains(666));
        System.out.println(a.contains("666"));
        System.out.println(a.containsAll(object));
        Object[] object1 = new Object[]{665,"wsm"};
        System.out.println(a.containsAll(object1));
        System.out.println(a.remove("wsm"));
        System.out.println(a.removeAll(object));
        System.out.println(a.indexOf("abcd"));
        System.out.println(a.lastIndexOf("123"));
        System.out.println(a.set(1,"yes"));
//        System.out.println(a.equals(a));
        ArrayListOfMyList b = new ArrayListOfMyList();
        b.addAll(a.toArray());
        System.out.println(b.toString());
        System.out.println(a.size()==b.size());
        System.out.println(a.equals(b));
        b.clear();
        System.out.println(b.size());
        System.out.println(b.toString());
         */
    }
}
