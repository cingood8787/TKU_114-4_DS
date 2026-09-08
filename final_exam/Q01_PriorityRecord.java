import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Q01_PriorityRecord {

    public record Job(String id, int priority, long sequence) {}

    public static List<String> processOrder(List<Job> jobs) {

        List<String> result = new ArrayList<>();

        if (jobs == null || jobs.isEmpty()) {
            return result;
        }

        Comparator<Job> comparator =
                Comparator.comparingInt(Job::priority)
                        .thenComparingLong(Job::sequence)
                        .thenComparing(
                                Job::id,
                                Comparator.nullsFirst(String::compareTo)
                        );

        PriorityQueue<Job> queue =
                new PriorityQueue<>(comparator);

        for (Job job : jobs) {
            if (job != null) {
                queue.offer(job);
            }
        }

        while (!queue.isEmpty()) {
            Job job = queue.poll();
            result.add(job.id());
        }

        return result;
    }

    public static void main(String[] args) {

        List<Job> jobs = new ArrayList<>();

        jobs.add(new Job("A", 2, 3));
        jobs.add(new Job("B", 1, 5));
        jobs.add(new Job("C", 1, 2));
        jobs.add(new Job("D", 1, 2));
        jobs.add(null);

        System.out.println(processOrder(jobs));
    }
}