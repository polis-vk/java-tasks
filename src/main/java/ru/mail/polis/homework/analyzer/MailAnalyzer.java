package ru.mail.polis.homework.analyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailAnalyzer implements TextAnalyzer {

    private final Pattern pattern = Pattern.compile("(\\W|^)[\\w.\\-]{0,25}@(gmail)\\.com(\\W|$)");

    @Override
    public boolean examine(String text){

        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return true;
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.MAIL;
    }

}
