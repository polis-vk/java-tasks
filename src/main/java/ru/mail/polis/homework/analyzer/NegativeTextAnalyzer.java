package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final FilterType filterType;
    private static final String[] negativeEmotions = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        this.filterType = FilterType.NEGATIVE_TEXT;
    }

    @Override
    public FilterType getType() {
        return this.filterType;
    }

    @Override
    public FilterType makeAnalysis(String text) {
        for (String negativeEmotion : negativeEmotions) {
            if (text.contains(negativeEmotion)) {
                return this.filterType;
            }
        }
        return FilterType.GOOD;
    }
}
