package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {
    final int PRIORITY = 4;
    private static final FilterType typeOfFilter = FilterType.CUSTOM;
    private final int sameWordsNumber;

    public CustomFilter(int sameWordsNumber) {
        this.sameWordsNumber = sameWordsNumber;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public FilterType getType() {
        return typeOfFilter;
    }

    @Override
    public boolean analyze(String text) {
        int textCounter = 1;
        if (text == null) {
            return false;
        }

        String[] words = text.split(" ");
        if (sameWordsNumber > words.length) {
            return false;
        }

        for (int i = 0; i < words.length - 1; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].equals(words[j])) {
                    textCounter++;
                    if (textCounter >= sameWordsNumber) {
                        return true;
                    }
                }
            }
            textCounter = 1;
        }
        return false;
    }
}
