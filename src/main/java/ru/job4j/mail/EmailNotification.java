package ru.job4j.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String pattern
                = "subject = Notification {username} to email {email}."
                + System.lineSeparator()
                + "body = Add a new event to {username}";
        pattern.replaceAll("\\{username}", user.getUsername());
        pattern.replaceFirst("\\{email}", user.getEmail());
        System.out.println(pattern);
    }

    public void close() {

    }

    public void send(String subject, String body, String email) {

    }
}
