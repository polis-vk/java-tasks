package ru.mail.polis.homework.analyzer;

import java.util.Locale;
import java.util.Objects;

public class CapsLockAnalyzer implements TextAnalyzer{
    private final FilterType type = FilterType.CAPS_LOCK;

    @Override
    public boolean analyze(String text) {
        return !Objects.equals(text, text.toUpperCase()) || text.toUpperCase().equals(text.toLowerCase());
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
