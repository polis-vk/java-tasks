package ru.mail.polis.homework.analyzer;

public class NegativeWordsTextAnalyzer extends SpamTextAnalyzer {

    public NegativeWordsTextAnalyzer() {
        super(new String[]{"=(", ":(", ":|"});
    }

    @Override
    public boolean analyzeText(String text) {
        for (String negativeWord : negativeWords) {
            if (text.contains(negativeWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
