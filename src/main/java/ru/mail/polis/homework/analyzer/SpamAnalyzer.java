package ru.mail.polis.homework.analyzer;
import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {

    private final String[] spamWords;
    private final FilterType filterType;

    protected SpamAnalyzer(String[] spamWords, FilterType filteType) {
        this.spamWords = Arrays.copyOf(spamWords, spamWords.length);
        this.filterType = filterType;
    }

    @Override
    public boolean analyze(String text) {
        for (String word : spamWords) {
            if (text.contains(word)){
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
