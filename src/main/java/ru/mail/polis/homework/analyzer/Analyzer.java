package ru.mail.polis.homework.analyzer;

public abstract class Analyzer implements TextAnalyzer {
    protected String[] arrayStrings;
    protected FilterType type;

    Analyzer(String[] arrayStrings) {
        this.arrayStrings = arrayStrings;
    }

    @Override
    public boolean filterWorked(String str) {
        for (String elem : arrayStrings) {
            if (str.contains(elem)) {
                return true;
            }
        }
        return false;
    }

    public abstract FilterType getType();
}
