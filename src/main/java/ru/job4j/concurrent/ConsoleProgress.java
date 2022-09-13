package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }

    @Override
    public void run() {

        String[] process = {"\\", "|", "/"};
        while (!Thread.currentThread().isInterrupted()) {
            for (int i = 0; i < process.length; i++) {
                System.out.print("\rLoading... "
                        + process[i]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
