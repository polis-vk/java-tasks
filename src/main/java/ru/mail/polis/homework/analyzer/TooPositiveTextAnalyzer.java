package ru.mail.polis.homework.analyzer;

import java.util.regex.Pattern;

public class TooPositiveTextAnalyzer implements TextAnalyzer {
    @Override
    public FilterType getFilterType(String str) {
        String regex = "[:=]-?\\){2,}"; //нахождение смайликов с двумя и более закрывающимися скобками
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(str).find()) {
            return FilterType.TOO_POSITIVE_TEXT;
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
