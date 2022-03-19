package ru.mail.polis.homework.analyzer;

public class NegativeTextOrSpamAnalyzer implements TextAnalyzer {

    private final String[] FILTER_WORDS;
    private final FilterType FILTER_TYPE;

    public NegativeTextOrSpamAnalyzer(String[] words) {
        if (words == null) {
            FILTER_WORDS = new String[]{"=(", ":(", ":|"};
            FILTER_TYPE = FilterType.NEGATIVE_TEXT;
        } else {
            FILTER_WORDS = words;
            FILTER_TYPE = FilterType.SPAM;
        }
    }

    @Override
    public FilterType getFilterType() {
        return FILTER_TYPE;
    }

    @Override
    public boolean doAnalyze(String text) {
        for (String word : FILTER_WORDS) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
