import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CollectionChoiceReport {

    public static void main(String[] args) {

        // =====================================
        // 1. 保留搜尋紀錄且允許重複
        // =====================================
        System.out.println("===== 需求 1：搜尋紀錄 =====");
        System.out.println("Interface：List");
        System.out.println("Implementation：ArrayList");

        List<String> searchHistory = new ArrayList<>();

        searchHistory.add("Java");
        searchHistory.add("Python");
        searchHistory.add("Java");

        System.out.println("操作結果：" + searchHistory);
        System.out.println();


        // =====================================
        // 2. 保存不重複會員編號
        // =====================================
        System.out.println("===== 需求 2：會員編號 =====");
        System.out.println("Interface：Set");
        System.out.println("Implementation：HashSet");

        Set<String> memberIds = new HashSet<>();

        memberIds.add("M001");
        memberIds.add("M002");
        memberIds.add("M003");

        boolean duplicateAdded = memberIds.add("M002");

        System.out.println("會員編號：" + memberIds);
        System.out.println("重複加入 M002 是否成功：" + duplicateAdded);
        System.out.println();


        // =====================================
        // 3. 以學號查詢成績
        // =====================================
        System.out.println("===== 需求 3：學號查詢成績 =====");
        System.out.println("Interface：Map");
        System.out.println("Implementation：HashMap");

        Map<String, Integer> scores = new HashMap<>();

        scores.put("S001", 85);
        scores.put("S002", 92);
        scores.put("S003", 76);

        System.out.println("S001 成績：" + scores.get("S001"));
        System.out.println("S002 成績：" + scores.get("S002"));
        System.out.println();


        // =====================================
        // 4. 依到達順序處理列印工作
        // =====================================
        System.out.println("===== 需求 4：列印工作 =====");
        System.out.println("Interface：Queue");
        System.out.println("Implementation：ArrayDeque");

        Queue<String> printQueue = new ArrayDeque<>();

        printQueue.offer("報告.pdf");
        printQueue.offer("作業.docx");
        printQueue.offer("圖片.png");

        System.out.println("列印 Queue：" + printQueue);

        System.out.println("處理：" + printQueue.poll());
        System.out.println("處理：" + printQueue.poll());

        System.out.println("剩餘工作：" + printQueue);
        System.out.println();


        // =====================================
        // 5. 復原最近操作
        // =====================================
        System.out.println("===== 需求 5：復原最近操作 =====");
        System.out.println("Interface：Deque");
        System.out.println("Implementation：ArrayDeque");

        Deque<String> undoStack = new ArrayDeque<>();

        undoStack.push("輸入 Hello");
        undoStack.push("輸入 Java");
        undoStack.push("刪除 Java");

        System.out.println("操作歷程：" + undoStack);

        String undo = undoStack.pop();

        System.out.println("復原：" + undo);
        System.out.println("復原後：" + undoStack);
    }
}