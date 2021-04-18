package ru.mail.polis.homework.analyzer;

/*
 * Limits the number of consecutive repeated words
 * */
public class CustomAnalyzer implements TextAnalyzer {
    private static final FilterType FILTER_TYPE = FilterType.CUSTOM;

    private final int maxRepeatsCount;

    public CustomAnalyzer(int maxRepeatsCount) {
        this.maxRepeatsCount = maxRepeatsCount;
    }

    @Override
    public FilterType filterType() {
        return FILTER_TYPE;
    }

    @Override
    public boolean analyze(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        if (maxRepeatsCount == 0) {
            return true;
        }

        String[] words = text.split(" ");
        int repeatsCount = 1;

        for (int i = 1; i < words.length; ++i) {
            if (words[i].equals(words[i - 1])) {
                if (++repeatsCount > maxRepeatsCount) {
                    return true;
                }
            } else {
                repeatsCount = 1;
            }
        }

        return false;
    }
}
