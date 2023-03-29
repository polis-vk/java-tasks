package ru.mail.polis.homework.analyzer;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;

    public SpamAnalyzer(String[] spam) {

        this.spam = spam;
    }

    public FilterType getFilterType(String str) {
        for (String spamWord : spam) {
            Pattern pattern = Pattern.compile(Pattern.quote(spamWord), Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
