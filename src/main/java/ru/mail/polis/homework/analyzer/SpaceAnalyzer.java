package ru.mail.polis.homework.analyzer;

public class SpaceAnalyzer implements TextAnalyzer {

    @Override
    public FilterType getType() {
        return FilterType.SPACE;
    }

    @Override
    public boolean check(String str) {
        return str.contains(" ");
    }
}
