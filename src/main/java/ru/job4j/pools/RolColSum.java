package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] array = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            array[i] = calculations(matrix, i);
        }
        return array;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] array = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            array[i] = getTask(matrix, i).get();
        }
        return array;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> calculations(matrix, i));
    }

    private static Sums calculations(int[][] matrix, int i) {
        int row = 0;
        int col = 0;
        for (int k = 0; k < matrix[i].length; k++) {
            row += matrix[i][k];
            col += matrix[k][i];
        }
        Sums sums = new Sums();
        sums.setRowSum(row);
        sums.setColSum(col);
        return sums;
    }
}
