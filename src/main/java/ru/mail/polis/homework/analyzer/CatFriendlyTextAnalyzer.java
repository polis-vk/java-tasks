package ru.mail.polis.homework.analyzer;

public class CatFriendlyTextAnalyzer implements TextAnalyzer {
    public CatFriendlyTextAnalyzer() {

    }

    @Override
    public boolean detected(String expression) {
        return filter.detected(expression) || expression.equals(expression.toUpperCase()); // Cats don't like loud scream
    }

    @Override
    public int getPriority() {
        return priority_;
    }

    @Override
    public FilterType getFilterType() {
        return analyzerType_;
    }

    static SpamAnalyzer filter = new SpamAnalyzer(new String[]{"валерьянка", "фейерверк", "собака",
                                                               "ребёнок", "огурец", "пылесос"}); // No comments needed
    static final int priority_ = 4;
    static final FilterType analyzerType_ = FilterType.CAT_FRIENDLY;
}
