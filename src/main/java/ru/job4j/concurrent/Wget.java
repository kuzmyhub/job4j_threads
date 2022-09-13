package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Loading...");
                        for (int i = 0; i <= 100; i++) {
                            System.out.print("\rLoading " + i + "%");
                            Thread.sleep(300);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
