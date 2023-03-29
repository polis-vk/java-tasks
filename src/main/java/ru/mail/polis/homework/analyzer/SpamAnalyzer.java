package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends InclusionFinder implements TextAnalyzer {

    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public boolean checkCorrection(String text) {
        return checkInclusion(text, spam);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
