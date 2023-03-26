package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {

    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public boolean checkCorrection(String text) {
        return TextAnalyzer.checkInclusion(text, spam);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
