package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter implements TextAnalyzer {

    private FilterType typePriority = FilterType.NEGATIVE_TEXT;
    private String[] negativeWords = {"=(", ":(", ":|"};

    @Override
    public FilterType getTypePriority() {
        return typePriority;
    }

    @Override
    public FilterType getTypeOfFilter(String str) {
        return FindInText.find(negativeWords, str, typePriority);
    }

}
