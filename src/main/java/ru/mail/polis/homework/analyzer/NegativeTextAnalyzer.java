package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final byte priority = 3;

    public boolean check(String str) {
        if (str.contains("=(") || str.contains(":(") || str.contains(":|")) {
            return true;
        }
        return false;
    }

    public FilterType filter() {
        return FilterType.NEGATIVE_TEXT;
    }

     public byte priority() {
         return priority;
     }
}
