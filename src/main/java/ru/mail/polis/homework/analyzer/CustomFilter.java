package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {

    public CustomFilter() {
    }

    //Нельзя использовать любы идущие друг за другом одинаковые буквы

    @Override
    public FilterType analyzer(String str) {
        for (int i = 1; i < str.length(); i++) {
            if(str.charAt(i-1) == str.charAt(i)){
                return FilterType.CUSTOM;
            }
        }
        return FilterType.GOOD;
    }
}
