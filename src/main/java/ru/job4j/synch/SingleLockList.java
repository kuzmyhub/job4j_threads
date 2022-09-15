package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("list")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public void add(T value) {
        synchronized (list) {
            list.add(value);
        }
    }

    public T get(int index) {
        synchronized (list) {
            return list.get(index);
        }
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    public List<T> copy(List<T> list) {
        return Collections.synchronizedList(list);
    }
}
