package MyList;

//单继承，多实现
public class MyListOfArrayList implements MyList {

    private Object[] objectArray;

    /**集合中对象的实际个数*/
    private int size;

    public static final int DEFAULT_SIZE = 16;

    MyListOfArrayList() {
        this.objectArray = new Object[DEFAULT_SIZE];
        this.size = 0;
    }

    /**
     *
     * @param args 可变参数，长度不固定，可以任意长度,如果还有参数，就不能放到可变参数的后面
     */
    MyListOfArrayList(Object ... args) {
        this.objectArray = args;
        this.size = args.length;
    }

    /**
     * 获取 list 第 index 个元素
     *
     * @param index
     * @return
     */
    @Override
    public Object get(int index) throws IndexOutOfBoundsException{
        /**
         * 异常处理：两种方式
         * 1.抓,捕获
         * try-catch-finally
         * 2.抛，抛出
         * throws
         */
        if (index < 0 || index >= this.size) {
            //出现异常的地方不进行处理，抛出异常，抛给调用该方法的地方
            throw new IndexOutOfBoundsException("数组越界");
        }
        return this.objectArray[index];
    }

    /**
     * 设置 list 第 index 个元素为 element
     *
     * @param index
     * @param element * @return
     */
    public Object set(int index, Object element) {
        if (index < 0 || index >= this.size) {
            //出现异常的地方不进行处理，抛出异常，抛给调用该方法的地方
            throw new IndexOutOfBoundsException("数组越界");
        }
        this.objectArray[index] = element;
        return this.objectArray[index];
    }

    /**
     * 获取 list 中是否包含某个元素
     *
     * @param o
     * @return
     */
    public boolean contains(Object o) {
        return this.indexOf(o) >= 0;
    }

    /**
     * 获取 list 是否包含数组的所有元素
     *
     * @param c
     * @return
     */
    public boolean containsAll(Object[] c) {
        for (Object obj : c) {
            if (!this.contains(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取 list 的元素个数
     *
     * @return
     */
    public int size() {
        return this.size;
    }

    /**
     * 获取一个元素和 list 一样的数组
     *
     * @return
     */
    public Object[] toArray() {

        Object[] array = new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            array[i] = this.objectArray[i];
        }
        return array;
    }

    /**
     * 获取 list 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * 向 list 中添加一个元素
     *
     * @param e
     * @return 成功返回 true，失败返回 false
     */
    public boolean add(Object e) {
        if (this.size + 1 > objectArray.length) {
            Object[] newArray = new Object[objectArray.length * 2];
            System.arraycopy(this.objectArray, 0, newArray, 0, this.size);
            this.objectArray = newArray;
        }
        objectArray[this.size] = e;
        this.size ++;
        return true;
    }

    /**
     * 将 e 中所有元素添加到 list 中
     *
     * @param e
     * @return 成功返回 true，失败返回 false
     */
    public boolean addAll(Object[] e) {
        if (e == null) {
            return false;
        }
        if (e.length + size > objectArray.length) {
            int newSize = objectArray.length;
            while (newSize > e.length + size) {
                newSize *= 2;
            }
            Object[] newArray = new Object[newSize];
            System.arraycopy(this.objectArray, 0, newArray, 0, this.size);
            objectArray = newArray;
        }

        for (int i = 0; i < e.length; i++) {
            objectArray[this.size + i] = e[i];
        }
        this.size += e.length;
        return true;
    }

    /**
     * 从 list 中删除第一个和  e 相等的元素
     *
     * @param e
     * @return 成功返回 true，失败或元素不在 list 中返回 false
     */
    @Override
    public boolean remove(Object e) {
        int index = indexOf(e);
        if (index == -1) {
            return false;
        }
        for (int i = index; i < this.size - 1; i++) {
            objectArray[i] = objectArray[i + 1];
        }
        this.size --;
        return true;
    }

    /**
     * 从 list 中删除所有 e 中的元素
     *
     * @param e
     * @return
     */
    public boolean removeAll(Object[] e) {
        if (e == null) {
            return false;
        }
        boolean isRemoved = false;

        for (Object o : e) {
            if (this.remove(o)) {
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    /**
     * 清空 list
     */
    public void clear() {
        this.size = 0;
        this.objectArray = new Object[DEFAULT_SIZE];
    }

    /**
     * 返回当前 list 与 o 是否相等
     *
     * @param o
     * @return
     */
    public boolean equals(Object o) {     //有误
        if (o == this) {
            return true;
        }
        if (o instanceof MyListOfArrayList) {
            MyListOfArrayList obj = (MyListOfArrayList) o;
            if (obj.size == this.size) {
                for (int i = 0; i < this.size; i++) {
                    if (this.objectArray[i] == null) {
                        if (obj.objectArray[i] != null) {
                            return false;
                        } else {
                            continue;
                        }
                    }
                    if (!this.objectArray[i].equals(obj.objectArray[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 获取 o 在 list 中第一次出现的下标
     *
     * @param o
     * @return
     */
    public int indexOf(Object o) {
        if (this.isEmpty()) {
            return -1;
        }

        if (o == null) {
            for (int i = 0; i < this.size; i++) {
                if (this.objectArray[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < this.size; i++) {
                if (o.equals(this.objectArray[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 获取 o 在 list 中最后一次出现的下标
     *
     * @param o
     * @return
     */
    public int lastIndexOf(Object o) {
        if (this.isEmpty()) {
            return -1;
        }

        if (o == null) {
            for (int i = this.size - 1; i >= 0; i--) {
                if (this.objectArray[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = this.size - 1; i >= 0; i--) {
                if (o.equals(this.objectArray[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 返回 list 中每一个元素组成的字符串
     * 如 list 中有3个整数1， 2， 3，那么返回如下
     * [1, 2, 3]
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("MyListOfArrayList=[");
        for (int i = 0; i < this.size; i++) {
            string.append(objectArray[i]);
            if (i != this.size - 1) {
                string.append(",");
            }
        }
        string.append("]");
        return string.toString();
    }

}
