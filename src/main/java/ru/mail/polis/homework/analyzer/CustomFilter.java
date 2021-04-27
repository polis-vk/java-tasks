package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {
    private static final FilterType TYPEOFFILTER = FilterType.CUSTOM;
    private final int sameWordsNumber;

    public CustomFilter(int sameWordsNumber) {
        this.sameWordsNumber = sameWordsNumber;
    }

    @Override
    public FilterType getType() {
        return TYPEOFFILTER;
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

            if (textCounter > words.length - sameWordsNumber) {
                return false;
            }
            textCounter = 1;
        }
        return false;
    }
}
