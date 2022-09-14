package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    static private final int ONE_SECOND_IN_MILLISECONDS = 1000;

    private final String readPath;

    private final int speed;

    public Wget(String path, int speed) {
        this.readPath = path;
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
                new URL(readPath).openStream()
        );
             FileOutputStream out = new FileOutputStream(url)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long endTime = System.currentTimeMillis();
                    if ((endTime - startTime) < ONE_SECOND_IN_MILLISECONDS) {
                        Thread.sleep(ONE_SECOND_IN_MILLISECONDS
                                - (endTime - startTime));
                    }
                }
                out.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
