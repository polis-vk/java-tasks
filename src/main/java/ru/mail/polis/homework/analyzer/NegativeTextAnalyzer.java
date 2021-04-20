package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer implements TextAnalyzer {
    private static final String[] NEGATIVE_WORDS = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer(){
        super(NEGATIVE_WORDS);
    }

    @Override
    public FilterType getTypeFilter() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean isCorrect(String text) {
        return text == null || text.isEmpty() || super.isCorrect(text);
    }
}
