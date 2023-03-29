package ru.mail.polis.homework.analyzer;

import java.util.Locale;
import java.util.Objects;

public class CapsLockAnalyzer implements TextAnalyzer {
    private final FilterType type = FilterType.CAPS_LOCK;

    @Override
    public boolean isCorrect(String text) {
        boolean includesUpperCase = false;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLowerCase(text.charAt(i))) {
                return true;
            }
            if (Character.isUpperCase(text.charAt(i))) {
                includesUpperCase = true;
            }
        }
        return (!includesUpperCase);
//        return !Objects.equals(text, text.toUpperCase()) || text.toUpperCase().equals(text.toLowerCase());
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
