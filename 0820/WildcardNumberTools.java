import java.util.ArrayList;
import java.util.List;

public class WildcardNumberTools {

    // 計算平均值
    static double average(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;

        for (Number value : values) {
            sum += value.doubleValue();
        }

        return sum / values.size();
    }

    // 找最大值
    static double maximum(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return Double.NaN;
        }

        double max = values.get(0).doubleValue();

        for (Number value : values) {
            if (value.doubleValue() > max) {
                max = value.doubleValue();
            }
        }

        return max;
    }

    // 加入 start 到 end 的整數
    static void addRange(List<? super Integer> target, int start, int end) {
        if (target == null || start > end) {
            return;
        }

        for (int i = start; i <= end; i++) {
            target.add(i);
        }
    }

    public static void main(String[] args) {

        // Integer 測試
        List<Integer> integers = new ArrayList<>();
        integers.add(10);
        integers.add(20);
        integers.add(30);

        System.out.println("Integer 平均：" + average(integers));
        System.out.println("Integer 最大值：" + maximum(integers));

        // Double 測試
        List<Double> doubles = new ArrayList<>();
        doubles.add(1.5);
        doubles.add(3.8);
        doubles.add(2.4);

        System.out.println("Double 平均：" + average(doubles));
        System.out.println("Double 最大值：" + maximum(doubles));

        // 空 List 測試
        List<Integer> empty = new ArrayList<>();

        System.out.println("空 List 平均：" + average(empty));
        System.out.println("空 List 最大值：" + maximum(empty));

        // addRange 測試
        List<Integer> numbers = new ArrayList<>();

        addRange(numbers, 1, 5);
        System.out.println("addRange 1~5：" + numbers);

        // start > end，不會加入資料
        addRange(numbers, 10, 5);
        System.out.println("start > end：" + numbers);

        // ? super Integer 也可以接 List<Number>
        List<Number> numberList = new ArrayList<>();

        addRange(numberList, 6, 8);
        System.out.println("List<Number>：" + numberList);
    }
}