package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends ContentSubstringAnalyzer {
    public SpamAnalyzer(String[] spam) {
        super(spam, FilterType.SPAM);
    }
}
