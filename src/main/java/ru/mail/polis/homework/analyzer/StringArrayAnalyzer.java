package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public abstract class StringArrayAnalyzer implements TextAnalyzer {
    protected String[] keys;

    @Override
    public boolean isValid(String text) {
        return Arrays.stream(keys).noneMatch(text::contains);
    }
}
