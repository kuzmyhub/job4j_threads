package ru.job4j.pool;

public class Job implements Runnable {
    @Override
    public void run() {
        System.out.println("run!");
    }
}
