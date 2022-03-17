package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer implements TextAnalyzer {

    String[] spam;

    public SpamTextAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType filterType(String text) {
        for (String element : spam) {
            if (text.contains(element)) {
                return type(text);
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType type(String text) {
        return FilterType.SPAM;
    }

    @Override
    public int priorityInfo() {
        return 0;
    }
}
