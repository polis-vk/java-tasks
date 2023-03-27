package ru.mail.polis.homework.analyzer;

public class Analyzer implements TextAnalyzer {
    protected String[] arrayStrings;
    protected long length;
    protected FilterType type;

    Analyzer(String[] arrayStrings) {
        this.arrayStrings = arrayStrings;
    }

    Analyzer() {
    }

    public boolean FilterWorked(String str) {
        for (String elem : arrayStrings) {
            if (str.contains(elem)) {
                return true;
            }
        }
        return false;
    }

    public FilterType getType() {
        return type;
    }
}
