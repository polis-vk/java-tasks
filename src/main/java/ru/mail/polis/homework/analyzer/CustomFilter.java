package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {


    //проверка, что текст состоит только из букв и знаков препинания
    @Override
    public FilterType analyze(String str) {
        String punctuationMarks = "!,.?;:'\" ";

        for (char c : str.toCharArray()) {
            if (!(Character.isLetter(c) || punctuationMarks.indexOf(c) != -1)) {
                return FilterType.CUSTOM;
            }
        }
        return null;
    }

    @Override
    public FilterType getPriority() {
        return FilterType.CUSTOM;
    }
}
