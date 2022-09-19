package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RolColSumTest {

    @Test
    public void whenSum0Then6And12() {
        int[][] in = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int expectedRow = 6;
        int expectedCol = 12;
        Sums[] sum = RolColSum.sum(in);
        assertThat(sum[0].getRowSum()).isEqualTo(expectedRow);
        assertThat(sum[0].getColSum()).isEqualTo(expectedCol);
    }

    @Test
    public void whenSum1Then15And15() {
        int[][] in = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int expectedRow = 15;
        int expectedCol = 15;
        Sums[] sum = RolColSum.sum(in);
        assertThat(sum[1].getRowSum()).isEqualTo(expectedRow);
        assertThat(sum[1].getColSum()).isEqualTo(expectedCol);
    }

    @Test
    public void whenSum2Then24And18() {
        int[][] in = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int expectedRow = 24;
        int expectedCol = 18;
        Sums[] sum = RolColSum.sum(in);
        assertThat(sum[2].getRowSum()).isEqualTo(expectedRow);
        assertThat(sum[2].getColSum()).isEqualTo(expectedCol);
    }

    @Test
    public void whenAsyncSum0Then6And12() throws ExecutionException, InterruptedException {
        int[][] in = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int expectedRow = 6;
        int expectedCol = 12;
        Sums[] sum = RolColSum.asyncSum(in);
        assertThat(sum[0].getRowSum()).isEqualTo(expectedRow);
        assertThat(sum[0].getColSum()).isEqualTo(expectedCol);
    }

    @Test
    public void whenAsyncSum1Then15And15() throws ExecutionException, InterruptedException {
        int[][] in = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int expectedRow = 15;
        int expectedCol = 15;
        Sums[] sum = RolColSum.asyncSum(in);
        assertThat(sum[1].getRowSum()).isEqualTo(expectedRow);
        assertThat(sum[1].getColSum()).isEqualTo(expectedCol);
    }

    @Test
    public void whenAsyncSum2Then24And18() throws ExecutionException, InterruptedException {
        int[][] in = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int expectedRow = 24;
        int expectedCol = 18;
        Sums[] sum = RolColSum.asyncSum(in);
        assertThat(sum[2].getRowSum()).isEqualTo(expectedRow);
        assertThat(sum[2].getColSum()).isEqualTo(expectedCol);
    }
}