package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile implements Parse {

    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    @Override
    public synchronized String getContent(Predicate<Character> predicate) {
        String output = "";
        try (BufferedInputStream in = new BufferedInputStream(
                new FileInputStream(file)
        )) {
            int data;
            while ((data = in.read()) > 0) {
                if (predicate.test((char) data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
