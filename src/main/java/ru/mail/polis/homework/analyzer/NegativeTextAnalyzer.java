package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private static final String[] EMOTION = new String[]{"=(", ":(", ":|"};

    SpamTextAnalyzer negativeTextAnalyzer = new SpamTextAnalyzer(EMOTION);

    @Override
    public boolean analyze(String text) {
        return negativeTextAnalyzer.analyze(text);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
