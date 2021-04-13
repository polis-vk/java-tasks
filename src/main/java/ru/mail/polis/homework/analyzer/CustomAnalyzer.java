package ru.mail.polis.homework.analyzer;

public class CustomAnalyzer implements TextAnalyzer {
    private static final int priority = 4;
    private static final FilterType filterType = FilterType.CUSTOM;

    private final int maxRepeatsCount;

    public CustomAnalyzer(int maxRepeatsCount) {
        this.maxRepeatsCount = maxRepeatsCount;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public FilterType filterType() {
        return filterType;
    }

    @Override
    public boolean analyze(String text) {
        String[] words = text.split(" ");

        int repeatsCount = 1;
        for (int i = 1; i < words.length; ++i) {
            if (words[i].equals(words[i - 1]) && ++repeatsCount > maxRepeatsCount) {
                return true;
            } else {
                repeatsCount = 1;
            }
        }

        return false;
    }
}
