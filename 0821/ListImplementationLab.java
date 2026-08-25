import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListImplementationLab {

    // 尾端新增
    public static void addLast(List<Integer> list, int value) {
        list.add(value);
    }

    // 指定位置插入
    public static void insert(List<Integer> list, int index, int value) {
        list.add(index, value);
    }

    // 搜尋，找到回傳索引，找不到回傳 -1
    public static int search(List<Integer> list, int value) {
        return list.indexOf(value);
    }

    // 刪除指定位置的元素
    public static int remove(List<Integer> list, int index) {
        return list.remove(index);
    }

    // 計算總和
    public static int sum(List<Integer> list) {
        int total = 0;

        for (int value : list) {
            total += value;
        }

        return total;
    }

    // 測試方法
    public static void testList(String name, List<Integer> list) {

        System.out.println("===== " + name + " =====");

        addLast(list, 10);
        addLast(list, 20);
        addLast(list, 30);

        System.out.println("尾端新增後：" + list);

        insert(list, 1, 15);
        System.out.println("指定位置插入後：" + list);

        System.out.println("搜尋 20 的位置：" + search(list, 20));

        int removed = remove(list, 2);
        System.out.println("刪除的元素：" + removed);
        System.out.println("刪除後：" + list);

        System.out.println("總和：" + sum(list));

        System.out.println();
    }

    public static void main(String[] args) {

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        testList("ArrayList", arrayList);
        testList("LinkedList", linkedList);
    }
}