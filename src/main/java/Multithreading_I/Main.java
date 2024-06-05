package Multithreading_I;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<SiteVisitCounter> counters = List.of(
                new UnsynchronizedCounterImpl(),
                new VolitileCounterImpl(),
                new SynchronizedBlockCounterImpl(),
                new AtomicIntegerCounterImp(),
                new ReentrantLockCounterImpl()
        );

        counters.forEach(
                e -> {
                    MultithreadingSiteVisitor visitor = new MultithreadingSiteVisitor(e);

                    // Тестирование с 100 потоками
                    visitor.visitMultithread(100);
                    try {
                        visitor.waitUntilAllVisited();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println(e.getClass());
                    System.out.println("Total milliseconds: " + visitor.getTotalTimeOfHandling());
                    System.out.println("Counter value: " + e.getVisitCount());
                    System.out.println();
                }
        );
    }
}