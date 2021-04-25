package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer{
    private final String[] SPAM;
    private final int PRIORITY = 1;

    SpamAnalyzer(String[] spam){
        this.SPAM = spam;
    }

    @Override
    public boolean analyze(String str) {
        if (TextAnalyzer.contains(this.SPAM, str)){
            return true;
        }

        return false;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }

    @Override
    public int getPriority() {
        return this.PRIORITY;
    }
}
