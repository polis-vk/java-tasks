package ru.mail.polis.homework.analyzer.custom;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

import java.util.StringTokenizer;

public class CapsAnalyzer implements TextAnalyzer {
    private final FilterType type = FilterType.CAPS;

    @Override
    public FilterType getType() {
        return type;
    }

    @Override
    public boolean analyze(String text) {
        boolean result = false;
        StringTokenizer st = new StringTokenizer(text, " ");
        while (st.hasMoreTokens() && !result) {
            String current = st.nextToken().replaceAll("[^А-я]", "");
            result = current.contains(current.toUpperCase()) && current.length() > 1;
        }
        return result;
    }
}
