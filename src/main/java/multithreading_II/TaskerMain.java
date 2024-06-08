package multithreading_II;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TaskerMain {
    public static void main(String[] args) {
        DataProcessor dataProcessor = new DataProcessor(10);

        List<String> taskNames = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            List<Integer> numbers = generateRandomList(100);
            String taskName = dataProcessor.addTask(numbers);
            taskNames.add(taskName);
        }

        while (dataProcessor.getActiveTasks() > 0) {
            System.out.println("Waiting for completing tasks. Active tasks: " + dataProcessor.getActiveTasks());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        taskNames.forEach(taskName -> {
            Optional<Integer> result = dataProcessor.getResult(taskName);
            result.ifPresent(value -> System.out.println("Task result: " + taskName + ": " + value));
        });

        dataProcessor.shutdown();

    }

    private static List<Integer> generateRandomList(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add((int) (Math.random() * 100));
        }
        return list;
    }
}
