package ru.job4j.cash;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountStorageTest {

    @Test
    void whenAdd() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Not found account by id"
                ));
        assertThat(firstAccount.amount()).isEqualTo(100);
    }

    @Test
    void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Not found account by id"
                ));
        assertThat(firstAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Not fount account by id"
                ));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Not fount account by id"
                ));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
    }

    @Test
    public void whenTransferThenMoneyIsEnough() {
        AccountStorage accountStorage = new AccountStorage();
        accountStorage.add(new Account(1, 100));
        accountStorage.add(new Account(2, 200));
        assertTrue(accountStorage.transfer(1, 2, 100));
    }

    @Test
    public void whenTransferThenMoneyIsNotEnough() {
        AccountStorage accountStorage = new AccountStorage();
        accountStorage.add(new Account(1, 100));
        accountStorage.add(new Account(2, 200));
        assertFalse(accountStorage.transfer(1, 2, 200));
    }

    @Test
    public void whenTransferThenFromIdNotFound() {
        AccountStorage accountStorage = new AccountStorage();
        accountStorage.add(new Account(2, 200));
        assertFalse(accountStorage.transfer(1, 2, 100));
    }

    @Test
    public void whenTransferThenToIdNotFound() {
        AccountStorage accountStorage = new AccountStorage();
        accountStorage.add(new Account(1, 100));
        assertFalse(accountStorage.transfer(1, 2, 100));
    }

    @Test
    public void whenTransferThenFromIdAndToIdNotFound() {
        AccountStorage accountStorage = new AccountStorage();
        assertFalse(accountStorage.transfer(1, 2, 100));
    }
}