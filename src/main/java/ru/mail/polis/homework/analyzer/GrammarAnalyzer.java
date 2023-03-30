package ru.mail.polis.homework.analyzer;

public class GrammarAnalyzer implements TextAnalyzer {

    @Override
    public FilterType getType() {
        return FilterType.NOT_GRAMMAR;
    }

    @Override
    public boolean filterWorked(String str) {
        char last = str.charAt(str.length() - 1);
        return !Character.isUpperCase(str.charAt(0)) || last != '!' && last != '.' && last != '?';
    }
}
