package multithreading_II;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

public class CalculateSumTask implements Callable<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(CalculateSumTask.class);

    private final List<Integer> list;
    private final String taskName;

    public CalculateSumTask(List<Integer> list, String taskName) {
        this.list = list;
        this.taskName = taskName;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(2000);
        logger.info("Выполнение в потоке: {}, Task name: {}", Thread.currentThread().getName(), taskName);
        return list.stream()
                .mapToInt(e -> e)
                .sum();
    }
}
