package ru.mail.polis.homework.analyzer;

public class CatFriendlyTextAnalyzer extends SpamAnalyzer {
    private static final String[] CAT_TRIGGERS = new String[]{"валерьянка", "фейерверк", "собака", "ребёнок", "огурец", "пылесос"};

    public CatFriendlyTextAnalyzer() {
        super(CAT_TRIGGERS);
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
