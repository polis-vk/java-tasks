package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends NegativeTextOrSpamAnalyzer {

    public SpamAnalyzer(String[] spam){
        super(spam);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
