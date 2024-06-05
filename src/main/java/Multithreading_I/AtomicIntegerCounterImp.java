package Multithreading_I;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerCounterImp implements SiteVisitCounter{

    AtomicInteger counterVisitors = new AtomicInteger(0);

    @Override
    public void incrementVisitCount() {
        try {
            counterVisitors.getAndIncrement();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getVisitCount() {
        return counterVisitors.get();
    }
}
