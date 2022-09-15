package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void whenPollThen2() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        List<Integer> in = List.of(1, 2);
        Thread producer = new Thread(() -> {
            for (Integer i : in) {
                queue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            queue.poll();
        });
        producer.start();
        try {
            producer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        consumer.start();
        try {
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(queue.poll()).isEqualTo(2);
    }

    @Test
    public void whenPollThen6() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        List<Integer> in = List.of(1, 2, 3);
        Thread producer = new Thread(() -> {
            for (Integer i : in) {
                queue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            queue.poll();
            queue.poll();
        });
        producer.start();
        consumer.start();
        try {
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(queue.poll()).isEqualTo(3);
    }
}