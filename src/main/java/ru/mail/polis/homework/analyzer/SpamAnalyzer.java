package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {

    final private String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
    }

    public boolean analyzer(String text) {
        for (String itSpam : spam) {
            int indexSpam = text.indexOf(itSpam);
            if (indexSpam != -1) {
                return true;
            }
        }
        return false;
    }

    public FilterType getType() {
        return FilterType.SPAM;
    }
}
