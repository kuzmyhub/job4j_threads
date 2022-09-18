package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FindForkJoinPool<T> extends RecursiveTask<Integer> {

    private static final int NOT_FOUND = -1;

    private static final int IN_HALF = 2;

    private static final int LIMIT_ARRAY_LENGTH = 10;

    private final T[] array;

    private final int firstElement;

    private final int lastElement;

    private final T searchElement;

    public FindForkJoinPool(T[] array, int firstElement,
                            int lastElement, T searchElement) {
        this.array = array;
        this.firstElement = firstElement;
        this.lastElement = lastElement;
        this.searchElement = searchElement;
    }

    @Override
    protected Integer compute() {
        if ((lastElement - firstElement) <= LIMIT_ARRAY_LENGTH) {
            int rsl = NOT_FOUND;
            for (int i = firstElement; i <= lastElement; i++) {
                if (array[i].equals(searchElement)) {
                    rsl = i;
                    break;
                }
            }
            return rsl;
        }
        int middleElement = (firstElement + lastElement) / IN_HALF;
        FindForkJoinPool<T> leftSearch = new FindForkJoinPool<>(
                array, firstElement, middleElement, searchElement
        );
        FindForkJoinPool<T> rightSearch = new FindForkJoinPool<>(
                array, middleElement + 1, lastElement, searchElement
        );
        leftSearch.fork();
        rightSearch.fork();
        Integer left = leftSearch.join();
        Integer right = rightSearch.join();
        return Math.max(left, right);
    }

    public static <T> int sort(T[] array, int from, int to, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new FindForkJoinPool<>(
                array, from, to, element
        ));
    }
}
