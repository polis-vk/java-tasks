package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private String[] spam;

    public SpamAnalyzer(String[] spam){
        this.spam = spam.clone();
    }
    public FilterType analyze(String text){
        for(String s:spam){
            if (text.contains(s)){
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
