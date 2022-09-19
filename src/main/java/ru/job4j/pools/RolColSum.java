package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] array = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int row = 0;
            int col = 0;
            for (int k = 0; k < matrix[i].length; k++) {
                row += matrix[i][k];
                col += matrix[k][i];
            }
            array[i] = new Sums();
            array[i].setRowSum(row);
            array[i].setColSum(col);
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
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            int row = 0;
            int col = 0;
            for (int k = 0; k < matrix.length; k++) {
                row += matrix[i][k];
                col += matrix[k][i];
            }
            sums.setRowSum(row);
            sums.setColSum(col);
            return sums;
        });
    }

    public static class Sums {

        private int rowSum;

        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }
}
