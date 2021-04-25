package ru.mail.polis.homework.analyzer;

public interface TextScanning {
    default boolean containsBadSymbols(String str, String[] badSymbols) {
        for (String symbol : badSymbols) {
            if (str.contains(symbol)) {
                return true;
            }
        }
        return false;
    }
}
