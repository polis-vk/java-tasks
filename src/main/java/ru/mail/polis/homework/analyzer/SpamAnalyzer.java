package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends Analyzer implements TextAnalyzer {
    private String[] spamExamples;

    public SpamAnalyzer(String[] spam) {
        spamExamples = spam;
    }

    @Override
    public FilterType analyze(String txt) { //это не та копипаста, которую вы ищете
        if (analyzeText(txt, spamExamples)) {
            return FilterType.SPAM;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
