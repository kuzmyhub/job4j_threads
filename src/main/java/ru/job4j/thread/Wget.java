package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    static private final int ONE_SECOND_IN_MILLISECONDS = 1000;

    private final String url;

    private final int speed;

    private final String file;

    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
            throw new IllegalArgumentException(String.format(
                    "Specify URL, download delay, file for downloaded data format %s %s %s",
                    "HTTP://SITE.COM/.../FILE.EXTENSION",
                    "NUMBER_IN_MILLISECONDS",
                    "FILE.EXTENSION"
            ));
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String file = args[2];
        Thread wget = new Thread(new Wget(url, speed, file));
        wget.start();
        wget.join();
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(
                new URL(url).openStream()
        );
             FileOutputStream out = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += dataBuffer.length;
                out.write(dataBuffer, 0, bytesRead);
                if (downloadData >= speed) {
                    long nowTime = System.currentTimeMillis();
                    long timeDifference = nowTime - startTime;
                    System.out.println(timeDifference);
                    downloadData = 0;
                    startTime = System.currentTimeMillis();
                    if ((timeDifference) < ONE_SECOND_IN_MILLISECONDS) {
                        Thread.sleep(ONE_SECOND_IN_MILLISECONDS - timeDifference);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
