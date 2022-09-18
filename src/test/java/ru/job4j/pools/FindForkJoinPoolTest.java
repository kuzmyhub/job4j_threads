package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class FindForkJoinPoolTest {

    @Test
    public void whenLookFor36550ThenFind() {
        Integer[] array = new Integer[100000];
        int searchWith = 0;
        int searchUp = array.length - 1;
        Integer searchObject = 36550;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertThat(
                FindForkJoinPool.sort(array, searchWith,
                        searchUp, searchObject)
        ).isEqualTo(searchObject);
    }

    @Test
    public void whenLookFor200000ThenNotFind() {
        Integer[] array = new Integer[100000];
        int searchWith = 0;
        int searchUp = array.length - 1;
        Integer searchObject = 200000;
        int expected = -1;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertThat(
                FindForkJoinPool.sort(array, searchWith,
                        searchUp, searchObject)
        ).isEqualTo(expected);
    }
}