package ru.mail.polis.homework.analyzer;

public class CatFriendlyTextAnalyzer extends SpamAnalyzer implements TextAnalyzer {
    static final FilterType analyzerType = FilterType.CAT_FRIENDLY;

    public CatFriendlyTextAnalyzer() {
        super(new String[]{"валерьянка", "фейерверк", "собака", "ребёнок", "огурец", "пылесос"});
    }

    @Override
    public boolean detected(String expression) {
        return super.detected(expression) || expression.equals(expression.toUpperCase()); // Cats don't like loud scream
    }

    @Override
    public FilterType getFilterType() {
        return analyzerType;
    }
}
