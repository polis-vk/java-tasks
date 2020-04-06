package ru.mail.polis.homework.analyzer;

import java.util.ArrayList;

public class SpamTextFiler implements TextAnalyzer {
    private String[] spamWords;

    public SpamTextFiler(String[] spamWords) {
        this.spamWords = spamWords;
    }

    @Override
    public FilterType getTypeOfFilter(String str) {
        return FindInText.find(spamWords, str, FilterType.SPAM);
    }
}
