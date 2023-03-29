package ru.mail.polis.homework.analyzer;

import java.util.ArrayList;
import java.util.List;

public class NegativeTextFilter implements TextAnalyzer {
    private List<String> negativeEmotions = new ArrayList<>();

    public NegativeTextFilter() {
        negativeEmotions.add("=(");
        negativeEmotions.add(":(");
        negativeEmotions.add(":|");
    }

    @Override
    public FilterType analyze(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }
        for (String emotion : negativeEmotions) {
            if (str.contains(emotion)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }

        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return FilterType.NEGATIVE_TEXT.getType();
    }
}
