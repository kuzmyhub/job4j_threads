package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.*;

class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        List<Integer> intList = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(intList);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl).isEqualTo(Set.of(1, 2));
    }
}