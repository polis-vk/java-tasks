package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer extends ForbiddenWords {

    public SpamTextAnalyzer(String[] spam) {
        forbiddenWords = spam;
        filterType = FilterType.SPAM;
    }
}
