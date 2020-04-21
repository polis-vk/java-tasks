package ru.mail.polis.homework.analyzer;

public class EmailTextAnalyzer implements TextAnalyzer {
    private String regex = "[a-z]+\\.[a-z]+@edu\\.spbstu\\.ru";

    @Override
    public FilterType analyze(String text) {
        int maxSizeOfEmail = 254;
        if (text.length() <= maxSizeOfEmail && text.matches(regex)) {
            return FilterType.VALID_EMAIL;
        }

        return FilterType.GOOD;
    }
}
