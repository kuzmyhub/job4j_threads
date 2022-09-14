package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {

    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            boolean rsl = false;
            Optional<Account> optional = getById(account.id());
            if (optional.isEmpty()) {
                accounts.put(account.id(), account);
                rsl = true;
            }
            return rsl;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            boolean rsl = false;
            Optional<Account> optional = getById(account.id());
            if (optional.isPresent()) {
                accounts.replace(account.id(), account);
                rsl = true;
            }
            return rsl;
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            boolean rsl = false;
            Optional<Account> optional = getById(id);
            if (optional.isPresent()) {
                accounts.remove(id);
                rsl = true;
            }
            return rsl;
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
