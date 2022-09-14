package ru.job4j.io;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Predicate;

public class StartUI {
    public static void main(String[] args) {
        File parse = Path.of(".gitignore").toFile();
        File out = Path.of("HelloWorld.txt").toFile();
        ParseFile parseFile = new ParseFile(parse);
        OutData outData = new OutData(out);
        Predicate<Character> getContent = x -> true;
        Predicate<Character> getContentWithOutUnicode = x -> x < 0x80;
        System.out.println(parseFile.getContent(getContent));
        System.out.println(parseFile.getContent(getContentWithOutUnicode));
        outData.saveContent("Hello World!");
    }
}
