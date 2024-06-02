public class SynchronizedBlockCounterImpl implements SiteVisitCounter{

    Integer counterVisitors = 0;

    @Override
    public void incrementVisitCount() {
        synchronized (this) {
            try {
                counterVisitors++;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int getVisitCount() {
        synchronized (this) {
            return counterVisitors;
        }
    }
}
