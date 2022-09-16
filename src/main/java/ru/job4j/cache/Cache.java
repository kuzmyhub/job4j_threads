package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        BiFunction<Integer, Base, Base> biFunction = (key, value) -> {
            Base stored = memory.get(model.getId());
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException(
                        "Version are not equal"
                );
            }
            Base updateModel = new Base(
                    key, value.getVersion() + 1
            );
            updateModel.setName(model.getName());
            return updateModel;
        };
        memory.computeIfPresent(model.getId(), biFunction);
        return true;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}
