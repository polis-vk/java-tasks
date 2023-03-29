package ru.mail.polis.homework.analyzer;

public class GrammarAnalyzer implements TextAnalyzer {
    private FilterType type;

    public void setType() {
        this.type = FilterType.NOT_GRAMMAR;
    }

    @Override
    public FilterType getType() {
        return this.type;
    }

    @Override
    public boolean filterWorked(String str) {
        char last = str.charAt(str.length() - 1);
        return !Character.isUpperCase(str.charAt(0)) || last != '!' && last != '.' && last != '?';
    }
}
