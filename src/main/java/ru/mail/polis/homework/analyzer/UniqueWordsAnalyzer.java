package ru.mail.polis.homework.analyzer;


public class UniqueWordsAnalyzer implements TextAnalyzer {

    private static final double PERCENTAGE_OF_UNIQUE_WORDS = 0.5;

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean doAnalyze(String text) {
        String copyText = text;
        copyText = copyText.replaceAll("\\pP", "").toLowerCase();
        while (copyText.contains("  ")) {
            copyText = copyText.replaceAll("  ", " ");
        }
        String[] words = copyText.split(" ");
        if (words.length == 1) {
            return false;
        }
        int uniqueWordsCount = 0;
        for (String word : words) {
            if (copyText.indexOf(word) == copyText.lastIndexOf(word)) {
                uniqueWordsCount++;
            }
        }
        return uniqueWordsCount == 0 || PERCENTAGE_OF_UNIQUE_WORDS > (double) uniqueWordsCount / words.length;
    }
}
