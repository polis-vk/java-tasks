package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    final private String[] smile = {"=(", ":(", ":|"};

    public boolean analyzer(String text) {
        for (String itSmile : smile) {
            int indexSpam = text.indexOf(itSmile);
            if (indexSpam != -1) {
                return true;
            }
        }
        return false;
    }

    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
