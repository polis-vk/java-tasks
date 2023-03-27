package ru.mail.polis.homework.analyzer;

public class GrammarAnalyzer extends Analyzer implements TextAnalyzer {

    GrammarAnalyzer() {
        this.type = FilterType.NOT_GRAMMAR;
    }

    @Override
    public boolean FilterWorked(String str) {
        char last = str.charAt(str.length() - 1);
        return !Character.isUpperCase(str.charAt(0)) || (last != '!' && last != '.' && last != '?');
    }
}
