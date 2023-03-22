package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final FilterType type = FilterType.SPAM;
    private final String[] arrayOfSpam;

    SpamAnalyzer(String[] spam) {
        this.arrayOfSpam = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public boolean isAnalyze(String str) {
        String[] splitStr = str.split(" ");
        for (String x : splitStr) {
            for (String spam : arrayOfSpam) {
                if (String.valueOf(x).equals(spam)) {
                    return false;
                }
            }
        }

        for (char x : str.toCharArray()) {
            for (String spam : arrayOfSpam) {
                if (String.valueOf(x).equals(spam)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
