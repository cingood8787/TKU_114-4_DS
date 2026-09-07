import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SocialNetworkGraph {

    private final Map<String, Set<String>> graph = new HashMap<>();

    // 新增使用者
    public void addUser(String user) {
        graph.putIfAbsent(user, new HashSet<>());
    }

    // 新增好友關係（無向）
    public boolean addFriend(String user1, String user2) {

        if (user1 == null || user2 == null) {
            return false;
        }

        if (user1.equals(user2)) {
            return false;
        }

        addUser(user1);
        addUser(user2);

        // 如果已經是好友，不重複加入
        if (graph.get(user1).contains(user2)) {
            return false;
        }

        graph.get(user1).add(user2);
        graph.get(user2).add(user1);

        return true;
    }

    // 解除好友
    public boolean removeFriend(String user1, String user2) {

        if (!graph.containsKey(user1)
                || !graph.containsKey(user2)) {
            return false;
        }

        if (!graph.get(user1).contains(user2)) {
            return false;
        }

        graph.get(user1).remove(user2);
        graph.get(user2).remove(user1);

        return true;
    }

    // 查詢好友
    public Set<String> friendsOf(String user) {

        if (!graph.containsKey(user)) {
            return new HashSet<>();
        }

        return new HashSet<>(graph.get(user));
    }

    // 查詢共同好友
    public Set<String> mutualFriends(
            String user1,
            String user2) {

        if (!graph.containsKey(user1)
                || !graph.containsKey(user2)) {
            return new HashSet<>();
        }

        Set<String> result =
                new HashSet<>(graph.get(user1));

        result.retainAll(graph.get(user2));

        return result;
    }

    // 查詢孤立使用者
    public List<String> isolatedUsers() {

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

    // 顯示整張 Adjacency List
    public void printGraph() {

        System.out.println("=== Social Network ===");

        List<String> users =
                new ArrayList<>(graph.keySet());

        users.sort(String::compareTo);

        for (String user : users) {

            List<String> friends =
                    new ArrayList<>(graph.get(user));

            friends.sort(String::compareTo);

            System.out.println(
                    user + " -> " + friends
            );
        }
    }

    public static void main(String[] args) {

        SocialNetworkGraph network =
                new SocialNetworkGraph();

        // 新增使用者
        network.addUser("Amy");
        network.addUser("Bob");
        network.addUser("Cindy");
        network.addUser("David");
        network.addUser("Eric");
        network.addUser("Frank");

        // 建立好友關係
        network.addFriend("Amy", "Bob");
        network.addFriend("Amy", "Cindy");
        network.addFriend("Bob", "Cindy");
        network.addFriend("Bob", "David");
        network.addFriend("Cindy", "David");

        network.printGraph();

        System.out.println();

        // 查詢 Amy 和 Bob 的共同好友
        System.out.println(
                "Amy & Bob mutual friends = "
                        + network.mutualFriends(
                                "Amy",
                                "Bob"
                        )
        );

        // 查詢孤立使用者
        System.out.println(
                "Isolated users = "
                        + network.isolatedUsers()
        );

        // 解除好友
        System.out.println();
        System.out.println("=== Remove Friend ===");

        boolean removed =
                network.removeFriend(
                        "Amy",
                        "Bob"
                );

        System.out.println(
                "Remove Amy-Bob = " + removed
        );

        System.out.println();

        network.printGraph();

        System.out.println();

        System.out.println(
                "Amy friends = "
                        + network.friendsOf("Amy")
        );
    }
}