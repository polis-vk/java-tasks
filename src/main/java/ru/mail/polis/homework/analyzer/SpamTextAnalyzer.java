package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer extends ForbiddenConstructionsAnalyzer {

    public SpamTextAnalyzer(String[] spam) {
        super(spam, FilterType.SPAM);
    }
}
