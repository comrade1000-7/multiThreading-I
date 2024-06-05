package multithreading_II;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DataProcessor {

    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);
    private final ExecutorService executorService;
    private final AtomicInteger counter;
    private final AtomicInteger activeTasks;
    private final Map<String, Integer> results;

    public DataProcessor(int numbersOfThread) {
        executorService = Executors.newFixedThreadPool(numbersOfThread);
        counter = new AtomicInteger(0);
        activeTasks = new AtomicInteger(0);
        results = new HashMap<>();
    }

    public String addTask(List<Integer> list) {
        counter.incrementAndGet();
        String taskName = "task " + counter;
        CalculateSumTask task = new CalculateSumTask(list, taskName);

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return task.call();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, executorService);

        activeTasks.incrementAndGet();
        completableFuture.thenAccept(result -> {
            synchronized (results) {
                results.put(taskName, result);
            }
            activeTasks.decrementAndGet();
        });

        return taskName;
    }

    public int getCounter() {
        return counter.get();
    }

    public int getActiveTasks() {
        return activeTasks.get();
    }

    public Optional<Integer> getResult(String taskName) {
        synchronized (results) {
            return Optional.ofNullable(results.get(taskName));
        }
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS))
                System.out.println("End of program");
        } catch (InterruptedException e) {
            executorService.shutdown();
            throw new RuntimeException(e);
        }
    }
}
