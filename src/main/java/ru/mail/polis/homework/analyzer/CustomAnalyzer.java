package ru.mail.polis.homework.analyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// проверяет содержит ли строка ссылки
public class CustomAnalyzer implements TextAnalyzer {
    private static final Pattern pattern = Pattern.compile("(https?:\\/\\/)?([\\w-]{1,32}\\.[\\w-]{1,32})[^\\s@]*");

    @Override
    public boolean analyze(String comment) {
        Matcher matcher = pattern.matcher(comment);
        return matcher.find();
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }
}
