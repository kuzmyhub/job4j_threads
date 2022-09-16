package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> task
            = new SimpleBlockingQueue<>(360);
    private int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                Runnable job;
                while (!task.isEmpty() || !Thread.currentThread().isInterrupted()) {
                    try {
                        job = task.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    job.run();
                    /*
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    */
                }
            }));
        }
        for (Thread t : threads) {
            t.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        task.offer(job);
    }

    public void shutdown() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 1; i <= 360; i++) {
            threadPool.work(new Job());
        }
        threadPool.shutdown();
    }
}
