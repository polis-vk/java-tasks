package ru.mail.polis.homework.analyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// проверяет содержит ли строка ссылки
public class CustomAnalyzer implements TextAnalyzer {

    @Override
    public FilterType analyze(String comment) {
        Pattern pattern = Pattern.compile("(https?:\\/\\/)?([\\w-]{1,32}\\.[\\w-]{1,32})[^\\s@]*");
        Matcher matcher = pattern.matcher(comment);
        if (matcher.find()) {
            return FilterType.CUSTOM;
        }
        return FilterType.GOOD;
    }
}
