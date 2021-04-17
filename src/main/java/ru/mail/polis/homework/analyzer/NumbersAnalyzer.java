package ru.mail.polis.homework.analyzer;

public class NumbersAnalyzer implements TextAnalyzer {
    @Override
    public boolean isNotCorrectString(String str) {
        for(char ch: str.toCharArray()){
            if(Character.isDigit(ch)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public FilterType getType() {
        return FilterType.NUMBERS;
    }
}
