package MyList;

/**
 * 根据接口，指定实现类，并且重写方法，底层使用数组或者链表实现，自行选择，有能力可以都写
 */
public interface MyList {

    /**
     * 获取 list 第 index 个元素
     * @param index
     * @return
     */
    Object get(int index);

    /**
     * 设置 list 第 index 个元素为 element
     * @param index
     * @param element
     * 	 * @return
     */
    Object set(int index, Object element);

    /**
     * 获取 list 中是否包含某个元素o
     * @param o
     * @return
     */
    boolean contains(Object o);

    /**
     * 获取 list 是否包含数组c的所有元素
     * @param c
     * @return
     */
    boolean containsAll(Object[] c);

    /**
     * 获取 list 的元素个数
     * @return
     */
    int size();

    /**
     * 获取一个元素和 list 一样的数组
     * @return
     */
    Object[] toArray();

    /**
     * 获取 list 是否为空(true)
     * @return
     */
    boolean isEmpty();

    /**
     * 向 list 中添加一个元素
     * @param e
     * @return 成功返回 true，失败返回 false
     */
    boolean add(Object e);

    /**
     * 将 e 中所有元素添加到 list 中
     * @param e
     * @return 成功返回 true，失败返回 false
     */
    boolean addAll(Object[] e);

    /**
     * 从 list 中删除第一个和  e 相等的元素
     * @param e
     * @return 成功返回 true，失败或元素不在 list 中返回 false
     */
    boolean remove(Object e);

    /**
     * 从 list 中删除所有数组 e 中的元素
     * @param e
     * @return
     */
    boolean removeAll(Object[] e);

    /**
     * 清空 list
     */
    void clear();

    /**
     * 返回当前 list 与 o 是否相等
     * @param o
     * @return
     */
    boolean equals(Object o);

    /**
     * 获取 o 在 list 中第一次出现的下标
     * @param o
     * @return
     */
    int indexOf(Object o);

    /**
     * 获取 o 在 list 中最后一次出现的下标
     * @param o
     * @return
     */
    int lastIndexOf(Object o);

    /**
     * 返回 list 中每一个元素组成的字符串
     * 如 list 中有3个整数1， 2， 3，那么返回如下
     *  [1, 2, 3]
     * @return
     */
    @Override
    String toString();

}
