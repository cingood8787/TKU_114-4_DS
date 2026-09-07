import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebsiteLinkGraph {

    // page -> outgoing links
    private final Map<String, Set<String>> graph = new HashMap<>();

    // 新增頁面
    public void addPage(String page) {
        if (page != null) {
            graph.putIfAbsent(page, new HashSet<>());
        }
    }

    // 新增 directed link：from -> to
    public boolean addLink(String from, String to) {

        if (from == null || to == null) {
            return false;
        }

        addPage(from);
        addPage(to);

        // Set 可避免重複 link
        return graph.get(from).add(to);
    }

    // 查詢 outgoing links
    public List<String> outgoingLinks(String page) {

        List<String> result = new ArrayList<>();

        if (!graph.containsKey(page)) {
            return result;
        }

        result.addAll(graph.get(page));
        result.sort(String::compareTo);

        return result;
    }

    // 查詢 incoming count
    public int incomingCount(String page) {

        if (!graph.containsKey(page)) {
            return 0;
        }

        int count = 0;

        for (Set<String> links : graph.values()) {
            if (links.contains(page)) {
                count++;
            }
        }

        return count;
    }

    // 找出沒有 incoming link 的頁面
    public List<String> pagesWithoutIncoming() {

        List<String> result = new ArrayList<>();

        for (String page : graph.keySet()) {
            if (incomingCount(page) == 0) {
                result.add(page);
            }
        }

        result.sort(String::compareTo);

        return result;
    }

    // 找出沒有 outgoing link 的頁面
    public List<String> pagesWithoutOutgoing() {

        List<String> result = new ArrayList<>();

        for (Map.Entry<String, Set<String>> entry
                : graph.entrySet()) {

            if (entry.getValue().isEmpty()) {
                result.add(entry.getKey());
            }
        }

        result.sort(String::compareTo);

        return result;
    }

    // 輸出完整報告
    public void report() {

        List<String> pages =
                new ArrayList<>(graph.keySet());

        pages.sort(String::compareTo);

        System.out.println(
                "=== Website Link Report ==="
        );

        for (String page : pages) {

            System.out.println(
                    page
                            + " | outgoing = "
                            + outgoingLinks(page)
                            + " | incoming count = "
                            + incomingCount(page)
            );
        }

        System.out.println();

        System.out.println(
                "No incoming pages = "
                        + pagesWithoutIncoming()
        );

        System.out.println(
                "No outgoing pages = "
                        + pagesWithoutOutgoing()
        );
    }

    public static void main(String[] args) {

        WebsiteLinkGraph graph =
                new WebsiteLinkGraph();

        // 建立網站頁面
        graph.addPage("Home");
        graph.addPage("About");
        graph.addPage("Products");
        graph.addPage("Contact");
        graph.addPage("Blog");
        graph.addPage("Help");

        // 建立 directed links
        graph.addLink("Home", "About");
        graph.addLink("Home", "Products");
        graph.addLink("Home", "Blog");

        graph.addLink("About", "Contact");

        graph.addLink("Products", "Contact");
        graph.addLink("Products", "Help");

        graph.addLink("Blog", "Products");

        // 測試重複 link
        boolean duplicate =
                graph.addLink(
                        "Home",
                        "About"
                );

        System.out.println(
                "Duplicate link added = "
                        + duplicate
        );

        System.out.println();

        graph.report();
    }
}