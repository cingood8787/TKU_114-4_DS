import java.util.ArrayDeque;
import java.util.Deque;

public class BrowserBackStack {

    // 使用 Deque 保存瀏覽歷程
    private Deque<String> history = new ArrayDeque<>();

    // 瀏覽新頁面
    public void visit(String page) {
        history.push(page);
        System.out.println("Visit: " + page);
    }

    // 返回上一頁
    public void back() {
        if (history.isEmpty()) {
            System.out.println("沒有可以返回的頁面");
            return;
        }

        history.pop();

        if (history.isEmpty()) {
            System.out.println("返回後目前沒有頁面");
        } else {
            System.out.println("Back to: " + history.peek());
        }
    }

    // 取得目前頁面
    public String current() {
        if (history.isEmpty()) {
            return "目前沒有頁面";
        }

        return history.peek();
    }

    public static void main(String[] args) {

        BrowserBackStack browser = new BrowserBackStack();

        // 連續測試至少五個操作
        browser.visit("Google");
        browser.visit("YouTube");
        browser.visit("GitHub");

        System.out.println("Current: " + browser.current());

        browser.back();

        System.out.println("Current: " + browser.current());

        browser.back();
        browser.back();

        // 空 stack 再 back，也不會發生例外
        browser.back();
    }
}