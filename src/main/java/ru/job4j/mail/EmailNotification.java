package ru.job4j.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class EmailNotification {

    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String subject = "subject = Notification {username} to email {email}."
                .replaceFirst("\\{username}", user.getUsername())
                .replaceFirst("\\{email}", user.getEmail());
        String body = "body = Add a new event to {username}"
                .replaceFirst("\\{username}", user.getUsername());

        pool.submit(new Runnable() {
            @Override
            public void run() {
                send(subject, body, user.getEmail());
            }
        });
    }

    public void close() {
        pool.shutdown();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void send(String subject, String body, String email) {

    }
}
