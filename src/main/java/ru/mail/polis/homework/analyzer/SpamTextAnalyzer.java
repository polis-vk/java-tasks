package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer implements TextAnalyzer {

    String[] spam;

    public SpamTextAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public boolean analyze(String text) {
        for (String element : spam) {
            if (text.contains(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}