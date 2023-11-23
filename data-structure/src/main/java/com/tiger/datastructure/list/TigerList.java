package com.tiger.datastructure.list;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/7/13 20:21
 * @Description:
 * @Version: 1.0
 **/
public interface TigerList<T> {

    int size();

    default boolean isEmpty() {
        return head() == null;
    }

    Node head();

    /**
     * 删除节点
     *
     * @param value
     * @return 如果节点存在则返回该节点，否则返回null
     */
    T remove(T value);

    /**
     * 添加元素
     *
     * @param value
     */
    void add(T value);

    /**
     * 查找指定位置的元素
     *
     * @param index
     * @return
     */
    default T get(int index) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("index:" + index);
        Node next = head();
        int count = 0;
        while (next != null) {
            if (count >= index) break;
            next = next.next();
            count++;
        }
        return (T) next.value();
    }

    /**
     * 判断元素是否存在
     *
     * @param value
     * @return
     */
    default boolean exists(T value) {
        Node next = head();
        do {
            if (next == null) return false;
            if (next.value().equals(value)) {
                return true;
            }
            next = next.next();
        } while (next != head());
        return false;
    }
}
