package ru.mail.polis.homework.analyzer;

public class CustomAnalyzer implements TextAnalyzer{
    private final String[] BAD_WORDS;
    private final int PRIORITY = 4;

    CustomAnalyzer(String[] badWords){
        this.BAD_WORDS = badWords;
    }

    @Override
    public boolean analyze(String str) {
        if (TextAnalyzer.contains(this.BAD_WORDS, str)){
            return true;
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return FilterType.CUSTOM;
    }

    @Override
    public int getPriority() {
        return this.PRIORITY;
    }
}
