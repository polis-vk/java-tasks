package ru.mail.polis.homework.analyzer;


public class UniqueWordsAnalyzer implements TextAnalyzer {

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean doAnalyze(String text) {
        String copyText = text;
        while (copyText.contains("  ")) {
            copyText = copyText.replaceAll("  ", " ");
        }
        copyText = copyText.replaceAll("\\pP", "").toLowerCase();
        System.out.println(copyText);
        String[] words = copyText.split(" ");
        for (String word : words) {
            if (copyText.indexOf(word) != copyText.lastIndexOf(word)) {
                return false;
            }
        }
        return true;
    }
}
