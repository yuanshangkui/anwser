package Mylist;

import java.util.Arrays;

public class IoMylist implements MyList{
    private Object[] a;
    private int size = 0;
    public IoMylist(int size) {
        this.a = new Object[size];
    }
    public IoMylist(){
        this.a=new Object[100];
    }
    @Override
    public Object get(int index){
        if(index>=size) {
            System.out.println("数组越界");
            return -1;
        }
        return a[index];
    }

    @Override
    public Object set(int index, Object element) {
        if(index>=size) return -1;
        this.a[index]=element;
        return a[index];
    }

    @Override
    public boolean contains(Object o){
        int i;
        for (i=0;i<a.length;i++){
            if (a[i]==null&&o==null){
                return true;
            }
            if(a[i]==null) continue;
            if (a[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Object[] c) {
        int i;
        for (i = 0;i < c.length;i++ ) {
            if (!this.contains(c[i])) return false;
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] b = new Object[size];
        System.arraycopy(a, 0, b, 0, size);
        return b;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean add(Object e) {
        if (size>=a.length) a= Arrays.copyOf(a,a.length*2);
        a[size]=e;
        size++;
        return a[size]==e;
    }

    @Override
    public boolean addAll(Object[] e) {
        for (int i = 0; i < e.length; i++) {
            this.add(e);
        }
        return this.containsAll(e);
    }

    @Override
    public boolean remove(Object e) {
        int i;
        for (i = 0;i<a.length;i++){
            if (a[i] == null && e==null) continue;
            if(a[i]==null) continue;
            if(a[i].equals(e)) {
                a[i]=null;
            }
        }
        return (!this.contains(e))||e==null;
    }

    @Override
    public boolean removeAll(Object[] e) {
        boolean b = true;
        for (int i = 0; i < e.length; i++) {
            if(!this.remove(e[i])) b=false;
        }
        return b;
    }

    @Override
    public void clear() {
        for(int i = 0;i<a.length;i++){
            this.a[i]=null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        int i;
        if(obj.getClass()!=this.getClass()) return false;
        IoMylist c=(IoMylist)obj;
        if(c.size()!=a.length)  return false;
        for(i=0;i<a.length;i++){
            if(a[i]==null&&c.get(i)==null) continue;
            if (a[i]==null||c.get(i)==null) return false;
            if(a[i].equals(c.get(i))) continue;
            return false;
        }
        return true;
    }

    @Override
    public int indexOf(Object o) {
        int i;
        for(i = 0;i<a.length;i++){
            if(a[i]==null&&o==null) {
                return i;
            }
            if (a[i]==null) continue;
            if(a[i].equals(o)) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int i;
        for(i = a.length-1;i>0;i--) {
            if(a[i]==null&&o==null) {
                return i;
            }
            if (a[i]==null) continue;
            if (a[i].equals(o)) return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        int i=0;
        StringBuilder s = new StringBuilder("[");
        s.append(a[i]);i++;
        for(;i<size;i++){
            s.append(",").append(a[i]);
        }
        s = new StringBuilder(s.toString().concat("]"));
        return s.toString();
    }
}