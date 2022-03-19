package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends BaseFindSomething {

    public SpamAnalyzer(String[] spam) {
        super(spam);
    }

    public FilterType getType() {
        return FilterType.SPAM;
    }
}
