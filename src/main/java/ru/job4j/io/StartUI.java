package ru.job4j.io;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Predicate;

public class StartUI {
    public static void main(String[] args) {
        File parse = Path.of(".gitignore").toFile();
        ParseFile parseFile = new ParseFile(parse);
        StartUI startUI = new StartUI();
        startUI.getDataFromFile(parseFile);
        startUI.getDataFromFileWithOutUnicode(parseFile);
        File out = Path.of("HelloWorld.txt").toFile();
        OutData outData = new OutData(out);
        outData.saveContent("Hello World!");
    }

    public void getDataFromFile(ParseFile parseFile) {
        Predicate<Character> predicateContent = x -> true;
        System.out.println(parseFile.getContent(predicateContent));
    }

    public void getDataFromFileWithOutUnicode(ParseFile parseFile) {
        Predicate<Character> predicateContentWithOutUnicode = x -> x < 0x80;
        System.out.println(parseFile.getContent(predicateContentWithOutUnicode));
    }
}
