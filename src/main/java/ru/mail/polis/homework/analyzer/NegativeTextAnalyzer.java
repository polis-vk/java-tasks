package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer implements TextAnalyzer{
    private static final String[] spam_ = {"=(", ":(", ":|"};

    NegativeTextAnalyzer() {
        super(spam_);
    }

    @Override
    public FilterType getFilter() {
        return FilterType.NEGATIVE_TEXT;
    }
}
