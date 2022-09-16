package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void whenPollThen2() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> in = List.of(1, 2);
        Thread producer = new Thread(() -> {
            for (Integer i : in) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
    public void whenPollThen6() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> in = List.of(1, 2, 3);
        Thread producer = new Thread(() -> {
            for (Integer i : in) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                queue.poll();
                queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    @Test
    public void whenPollThen12345() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer
                = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue queue = new SimpleBlockingQueue(5);
        List<Integer> in = List.of(1, 2, 3, 4, 5);
        Thread producer = new Thread(() -> {
            try {
                for (Integer i : in) {
                    queue.offer(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        Thread consumer = new Thread(() -> {
            while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                try {
                    buffer.add((Integer) queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();
        consumer.interrupt();
        producer.join();
        consumer.join();
        assertThat(buffer).isEqualTo(List.of(1, 2, 3, 4, 5));
    }
}