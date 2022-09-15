package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {

    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            Optional<Account> optional = Optional.ofNullable(
                    accounts.putIfAbsent(account.id(), account)
            );
            return optional.isEmpty();
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            Optional<Account> optional = Optional.ofNullable(
                    accounts.replace(account.id(), account)
            );
            return optional.isPresent();
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            Optional<Account> optional = Optional.ofNullable(
                    accounts.remove(id)
            );
            return optional.isPresent();
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            boolean rsl = false;
            Optional<Account> from = getById(fromId);
            Optional<Account> to = getById(toId);
            if (from.isPresent() && to.isPresent()
                    && (from.get().amount() >= amount)) {
                accounts.replace(fromId, new Account(from.get().id(),
                        from.get().amount() - amount));
                accounts.replace(toId, new Account(to.get().id(),
                        to.get().amount() + amount));
                rsl = true;
            }
            return rsl;
        }
    }
}
