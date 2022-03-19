package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends BaseFindSomething {

    public SpamAnalyzer(String[] spam) {
        super(spam);
    }

    public FilterType type() {
        return FilterType.SPAM;
    }
}
