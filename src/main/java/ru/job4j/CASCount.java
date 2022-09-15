package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer temp = count.get() + 1;
        Integer ref;
        do {
            ref = count.get();
        } while (!count.compareAndSet(ref, temp));
    }

    public int get() {
        if (count.get() == null) {
            throw new IllegalArgumentException(
                    "Count not incremented yet"
            );
        }
        return count.get();
    }

    public static void main(String[] args) {
        CASCount casCount = new CASCount();
        Thread first = new Thread(() -> {
            for (int i = 1; i <= 30; i++) {
                casCount.increment();
            }
        });

        Thread second = new Thread(() -> {
            for (int i = 1; i <= 30; i++) {
                casCount.increment();
            }
        });

        Thread third = new Thread(() -> {
            for (int i = 1; i <= 30; i++) {
                casCount.increment();
            }
        });

        first.start();
        second.start();
        third.start();

        try {
            first.join();
            second.join();
            third.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(casCount.get());
    }
}
