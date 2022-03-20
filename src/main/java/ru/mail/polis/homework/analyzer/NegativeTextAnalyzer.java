package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private static final String[] EMOTION = new String[]{"=(", ":(", ":|"};

    @Override
    public boolean analyze(String text) {
        SpamTextAnalyzer negativeTextAnalyzer = new SpamTextAnalyzer(EMOTION);
        return negativeTextAnalyzer.analyze(text);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
