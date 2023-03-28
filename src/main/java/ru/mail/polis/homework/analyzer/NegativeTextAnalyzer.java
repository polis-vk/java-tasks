package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    public FilterType analyze(String text) {
        return analyze(text, TextAnalyzer.NEGATIVE_EMOTIONS, FilterType.NEGATIVE_TEXT);
    }
}
