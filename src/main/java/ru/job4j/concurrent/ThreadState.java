package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread()
                        .getName())
        );
        first.start();

        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread()
                        .getName())
        );
        second.start();

        boolean selector = true;
        while (selector) {
            if (first.getState() == Thread.State.TERMINATED
                    && second.getState() == Thread.State.TERMINATED) {
                selector = false;
            }
        }
        System.out.println("Work is completed");
    }
}
