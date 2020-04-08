package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    private String[] spam;

    public SpamFilter(String[] spam) {
        this.spam = spam;
    }

    @Override
    public boolean getResult(String text) {
        for (String negativeEmotion : spam) {
            if (text.contains(negativeEmotion)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
