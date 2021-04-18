package ru.mail.polis.homework.analyzer;

public interface TextAnalyzer {
    public byte priority();
    public boolean check(String str);
    public FilterType filter();

    static TextAnalyzer createTooLongAnalyzer(long maxLength) {
        return new TooLongAnalyzer(maxLength);
    }

    static TextAnalyzer createSpamAnalyzer(String[] spam) {
        return new SpamAnalyzer(spam);
    }

    static TextAnalyzer createNegativeTextAnalyzer() {
        return new NegativeTextAnalyzer();
    }
    
    static TextAnalyzer createCustomAnalyzer() {
        return new CustomAnalyzer();
    }
}