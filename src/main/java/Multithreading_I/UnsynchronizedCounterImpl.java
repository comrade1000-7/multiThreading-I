package Multithreading_I;

public class UnsynchronizedCounterImpl implements SiteVisitCounter {
    int counterVisitors;

    @Override
    public void incrementVisitCount() {
        try {
            counterVisitors++;
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getVisitCount() {
        return counterVisitors;
    }
}
