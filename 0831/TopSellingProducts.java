```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopSellingProducts {

    static class Product {
        String id;
        int sales;

        public Product(String id, int sales) {
            this.id = id;
            this.sales = sales;
        }

        @Override
        public String toString() {
            return id + "|" + sales;
        }
    }

    public static List<Product> topKProducts(List<Product> products, int k) {

        List<Product> result = new ArrayList<>();

        if (products == null || k <= 0) {
            return result;
        }

        // 先合併重複商品 id 的銷量
        Map<String, Integer> salesMap = new HashMap<>();

        for (Product product : products) {

            if (product == null || product.id == null) {
                continue;
            }

            salesMap.put(
                    product.id,
                    salesMap.getOrDefault(product.id, 0) + product.sales
            );
        }

        /*
         * 使用固定大小 Min Heap
         *
         * Heap root 代表目前 Top-K 裡「最差」的商品：
         * 1. 銷量較少者較差
         * 2. 銷量相同時，id 字典序較大者較差
         */
        PriorityQueue<Product> heap = new PriorityQueue<>(
                (a, b) -> {
                    if (a.sales != b.sales) {
                        return Integer.compare(a.sales, b.sales);
                    }

                    return b.id.compareTo(a.id);
                }
        );

        // 將合併後的商品放入固定大小 Heap
        for (Map.Entry<String, Integer> entry : salesMap.entrySet()) {

            Product product = new Product(
                    entry.getKey(),
                    entry.getValue()
            );

            if (heap.size() < k) {
                heap.offer(product);
            } else {

                Product worst = heap.peek();

                boolean better =
                        product.sales > worst.sales
                        ||
                        (product.sales == worst.sales
                                && product.id.compareTo(worst.id) < 0);

                if (better) {
                    heap.poll();
                    heap.offer(product);
                }
            }
        }

        result.addAll(heap);

        // 最後依照題目要求排序
        // 銷量高 -> 低
        // 銷量相同 id 字典序小 -> 大
        result.sort(
                (a, b) -> {
                    if (a.sales != b.sales) {
                        return Integer.compare(b.sales, a.sales);
                    }

                    return a.id.compareTo(b.id);
                }
        );

        return result;
    }

    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();

        products.add(new Product("P003", 50));
        products.add(new Product("P001", 100));
        products.add(new Product("P002", 80));
        products.add(new Product("P001", 30));
        products.add(new Product("P004", 80));
        products.add(new Product("P003", 60));
        products.add(new Product("P005", 40));
        products.add(new Product("P002", 50));

        int k = 3;

        List<Product> result = topKProducts(products, k);

        System.out.println("=== Top " + k + " Products ===");

        for (Product product : result) {
            System.out.println(product);
        }
    }
}
```
