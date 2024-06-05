package multithreading_I;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultithreadingSiteVisitor {
    final SiteVisitCounter siteVisitCounter;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private long startTime;
    private long endTime;

    public MultithreadingSiteVisitor(SiteVisitCounter siteVisitCounter) {
        this.siteVisitCounter = siteVisitCounter;
    }

    public void visitMultithread(int numOfThreads) {
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numOfThreads; i++) {
            executorService.execute(siteVisitCounter::incrementVisitCount);
        }
    }

    public void waitUntilAllVisited() throws InterruptedException {
        executorService.shutdown();
        System.out.println("Waiting...");
        if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS))
            endTime = System.currentTimeMillis();
    }

    public long getTotalTimeOfHandling() {
        return endTime - startTime;
    }
}
