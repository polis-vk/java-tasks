package ru.mail.polis.homework.analyzer;

public class NegativeTextOrSpamAnalyzer implements TextAnalyzer {

    private String[] filter_words;
    private FilterType filter_type;

    public NegativeTextOrSpamAnalyzer(String[] words) {
        filter_words = words;
        filter_type = FilterType.SPAM;
    }

    public NegativeTextOrSpamAnalyzer() {
        filter_words = new String[]{"=(", ":(", ":|"};
        filter_type = FilterType.NEGATIVE_TEXT;
    }

    @Override
    public FilterType getFilterType() {

        return filter_type;
    }

    @Override
    public boolean doAnalyze(String text) {
        for (String word : filter_words) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
