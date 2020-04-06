package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter implements TextAnalyzer {

    private String[] negativeWords = {"=(", ":(", ":|"};

    @Override
    public FilterType getTypeOfFilter(String str) {
        return FindInText.find(negativeWords, str, FilterType.NEGATIVE_TEXT);
    }

}
