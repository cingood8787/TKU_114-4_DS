import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Q12_CampusDispatchSystem {

    public record Request(
            String id,
            String location,
            int priority,
            long sequence) {
    }

    private final Map<String, Set<String>> roads =
            new HashMap<>();

    private final Map<String, Request> requests =
            new HashMap<>();

    public boolean addLocation(String location) {

        if (location == null || location.isBlank()) {
            return false;
        }

        if (roads.containsKey(location)) {
            return false;
        }

        roads.put(location, new LinkedHashSet<>());

        return true;
    }

    public boolean addRoad(String first, String second) {

        if (first == null || second == null) {
            return false;
        }

        if (!roads.containsKey(first)
                || !roads.containsKey(second)) {
            return false;
        }

        if (first.equals(second)) {
            return false;
        }

        if (roads.get(first).contains(second)) {
            return false;
        }

        roads.get(first).add(second);
        roads.get(second).add(first);

        return true;
    }

    public boolean submit(Request request) {

        if (request == null
                || request.id() == null
                || request.location() == null) {
            return false;
        }

        if (requests.containsKey(request.id())) {
            return false;
        }

        if (!roads.containsKey(request.location())) {
            return false;
        }

        requests.put(request.id(), request);

        return true;
    }

    public Request nextReachable(String serviceCenter) {

        if (serviceCenter == null
                || !roads.containsKey(serviceCenter)) {
            return null;
        }

        Set<String> reachable =
                reachableLocations(serviceCenter);

        PriorityQueue<Request> queue =
                new PriorityQueue<>(
                        Comparator
                                .comparingInt(Request::priority)
                                .thenComparingLong(Request::sequence)
                );

        for (Request request : requests.values()) {

            if (reachable.contains(request.location())) {
                queue.offer(request);
            }
        }

        if (queue.isEmpty()) {
            return null;
        }

        Request next = queue.poll();

        requests.remove(next.id());

        return next;
    }

    private Set<String> reachableLocations(String start) {

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {

            String current = queue.poll();

            for (String next : roads.get(current)) {

                if (visited.add(next)) {
                    queue.offer(next);
                }
            }
        }

        return visited;
    }

    public List<String> route(
            String start,
            String target) {

        List<String> empty = new ArrayList<>();

        if (start == null
                || target == null
                || !roads.containsKey(start)
                || !roads.containsKey(target)) {

            return empty;
        }

        if (start.equals(target)) {

            List<String> result = new ArrayList<>();
            result.add(start);

            return result;
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        Map<String, String> predecessor =
                new HashMap<>();

        queue.offer(start);
        visited.add(start);

        boolean found = false;

        while (!queue.isEmpty() && !found) {

            String current = queue.poll();

            for (String next : roads.get(current)) {

                if (visited.add(next)) {

                    predecessor.put(next, current);

                    if (next.equals(target)) {
                        found = true;
                        break;
                    }

                    queue.offer(next);
                }
            }
        }

        if (!found) {
            return empty;
        }

        List<String> path = new ArrayList<>();

        String current = target;

        while (current != null) {

            path.add(0, current);

            if (current.equals(start)) {
                break;
            }

            current = predecessor.get(current);
        }

        return path;
    }

    public int pendingCount() {
        return requests.size();
    }
}
