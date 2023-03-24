package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer extends ForbiddenTextAnalyzer{
    public SpamTextAnalyzer(String[] spam) {
        forbiddenText = spam;
        filterType = FilterType.SPAM;
    }
}
