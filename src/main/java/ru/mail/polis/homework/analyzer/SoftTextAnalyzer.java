package ru.mail.polis.homework.analyzer;

public class SoftTextAnalyzer implements TextAnalyzer {
    private final String[] softWords = new String[]{"милый", "добрый", "котик", "зайка", "красивый"};

    public SoftTextAnalyzer() {
        super();
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SOFT_TEXT;
    }

    @Override
    public boolean isMatchFilter(String text) {
        for (String word : softWords) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
