package ru.mail.polis.homework.analyzer;


public class CustomAnalyzer implements TextAnalyzer {

    @Override
    public FilterType analyze(String text){
        if (text.equals(text.toLowerCase())){
            return FilterType.GOOD;
        }
        return FilterType.CUSTOM;
    }
}
