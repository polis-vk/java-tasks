package ru.mail.polis.homework.analyzer.custom;

import ru.mail.polis.homework.analyzer.FilterType;

public class SpamAnalyzer extends WrongWordsAnalyzer {
    private final FilterType type = FilterType.SPAM;

    public SpamAnalyzer(String[] spamMessages) {
        super(spamMessages);
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
