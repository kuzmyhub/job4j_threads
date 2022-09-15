package ru.job4j;

import java.sql.SQLOutput;

public class CountBarrier {

    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (total < count) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(0);
        Thread one = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            countBarrier.count();
        },
                "Master"
        );
        Thread two = new Thread(() -> {
            countBarrier.await();
            System.out.println(Thread.currentThread().getName());
        },
                "Slave"
        );
        one.start();
        two.start();
    }
}
