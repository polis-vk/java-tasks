package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends NegativeAnalyzer implements TextAnalyzer {
    private String[] spam;
    private final FilterType result = FilterType.SPAM;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    private void setSpam(String[] spam) {
        super.setSmiles(spam);
        super.setResult(result);
    }

    @Override
    public FilterType analyze(String text) {
        setSpam(spam);
        return super.analyze(text);
    }
}
