package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CASCountTest {

    @Test
    public void whenCountIncrement3x30Then90() throws InterruptedException {
        CASCount casCount = new CASCount();
        CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        Thread producerOne = new Thread(() -> {
            for (int i = 1; i <= 30; i++) {
                casCount.increment();
            }
        });
        Thread producerTwo = new Thread(() -> {
            for (int i = 1; i <= 30; i++) {
                casCount.increment();
            }
        });
        Thread producerThird = new Thread(() -> {
            for (int i = 1; i <= 30; i++) {
                casCount.increment();
            }
        });
        Thread consumer = new Thread(() -> {
            buffer.add(casCount.get());
        });
        producerOne.start();
        producerTwo.start();
        producerThird.start();
        producerOne.join();
        producerTwo.join();
        producerThird.join();
        consumer.start();
        consumer.join();
        assertThat(buffer.get(0)).isEqualTo(90);
    }

    @Test
    public void whenCountIncrement4x10Then40() throws InterruptedException {
        CASCount casCount = new CASCount();
        CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        Thread producerOne = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                casCount.increment();
            }
        });
        Thread producerTwo = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                casCount.increment();
            }
        });
        Thread producerThird = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                casCount.increment();
            }
        });
        Thread producerFourth = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                casCount.increment();
            }
        });
        Thread consumer = new Thread(() -> {
            buffer.add(casCount.get());
        });
        producerOne.start();
        producerTwo.start();
        producerThird.start();
        producerFourth.start();
        producerOne.join();
        producerTwo.join();
        producerThird.join();
        producerFourth.join();
        consumer.start();
        consumer.join();
        assertThat(buffer.get(0)).isEqualTo(40);
    }
}