package ru.mail.polis.homework.analyzer;

public class NegativeAnalyzer implements TextAnalyzer {
    final private static String[] ILLEGAL_SYMBOLS = {"=(", ":(", ":|"};

    public FilterType analyze(String text){
        for(String s:ILLEGAL_SYMBOLS){
            if (text.contains(s)){
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }
}