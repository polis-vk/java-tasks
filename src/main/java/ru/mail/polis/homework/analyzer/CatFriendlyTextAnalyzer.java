package ru.mail.polis.homework.analyzer;

public class CatFriendlyTextAnalyzer extends SpamAnalyzer implements TextAnalyzer {
    private static final String[] catTriggers = new String[]{"валерьянка", "фейерверк", "собака", "ребёнок", "огурец", "пылесос"};

    public CatFriendlyTextAnalyzer() {
        super(catTriggers);
    }

    @Override
    public boolean detected(String expression) {
        return super.detected(expression) || expression.equals(expression.toUpperCase()); // Cats don't like loud scream
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CAT_FRIENDLY;
    }
}
