package ru.mail.polis.homework.analyzer;

public interface SimpleFilter {
    default boolean textContain(String text, String[] checklist) {
        for (String element : checklist) {
            if (text.contains(element)) {
                return true;
            }
        }
        return false;
    }
}
