package ru.mail.polis.homework.analyzer;

public abstract class Analyzer implements TextAnalyzer {
    protected String[] arrayStrings;
    protected FilterType type;

    protected abstract void setType();

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

    @Override
    public FilterType getType() {
        return type;
    }
}
