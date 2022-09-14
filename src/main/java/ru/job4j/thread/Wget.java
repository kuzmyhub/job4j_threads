package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;

    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            throw new IllegalArgumentException(String.format(
                    "Specify URL and download delay format %s %s",
                    "HTTP://SITE.COM/.../FILE", "NUMBER_IN_MILLISECONDS"
            ));
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(
                new URL(url).openStream()
        );
             FileOutputStream out = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                long startTime = System.currentTimeMillis();
                long endTime = System.currentTimeMillis();
                if ((endTime - startTime) < speed) {
                    Thread.sleep((endTime - startTime) + speed);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
