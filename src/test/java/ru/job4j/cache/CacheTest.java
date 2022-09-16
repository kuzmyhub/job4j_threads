package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    public void whenAddThenTrue() {
        Cache cache = new Cache();
        Base baseOne = new Base(1, 1);
        assertTrue(cache.add(baseOne));
    }

    @Test
    public void whenAddThenFalse() {
        Cache cache = new Cache();
        Base baseOne = new Base(1, 1);
        cache.add(baseOne);
        Base baseTwo = new Base(1, 1);
        cache.add(baseTwo);
        assertFalse(cache.add(baseTwo));
    }

    @Test
    public void whenUpdateThenTrue() {
        Cache cache = new Cache();
        Base baseOne = new Base(1, 1);
        baseOne.setName("task");
        Base baseTwo = new Base(1, 1);
        baseTwo.setName("reTask");
        cache.add(baseOne);
        assertTrue(cache.update(baseTwo));
    }

    @Test
    public void whenUpdateThenException() {
        OptimisticException exception = assertThrows(
                OptimisticException.class,
                () -> {
                    Cache cache = new Cache();
                    Base baseOne = new Base(1, 1);
                    baseOne.setName("task");
                    Base baseTwo = new Base(1, 2);
                    baseTwo.setName("reTask");
                    cache.add(baseOne);
                    cache.update(baseTwo);
                });
        assertThat(exception.getMessage()).isEqualTo("Version are not equal");
    }

    @Test
    public void whenDeleteThenTrue() {
        Cache cache = new Cache();
        Base baseOne = new Base(1, 1);
        cache.add(baseOne);
        cache.delete(baseOne);
        assertTrue(cache.add(baseOne));
    }
}