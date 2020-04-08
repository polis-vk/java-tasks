package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    static private String[] negativeArray = {"=(", ":(", ":|"};

    public FilterType analyze(String text) {
        if (text == null || text == "") {
            return FilterType.GOOD;
        }
        for (int i = 0; i < negativeArray.length; i++) {
            if (text.contains(negativeArray[i])) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }
}
