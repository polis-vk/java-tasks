package ru.mail.polis.homework.analyzer;

public class NotConciseTextAnalyzer implements TextAnalyzer {
    final String[] parasiticWords = {"В общем,", "Короче,", "Значит,"};
    final static char priority = 3;

    @Override
    public FilterType filtering(String text) {
        for (String parasiticWord : parasiticWords) {
            if (countWord(text, parasiticWord) > 1) {
                return FilterType.NOT_CONCISE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    //количество вхождений подстроки в строку
    private int countWord(String str, String target) {
        return (str.length() - str.replace(target, "").length()) / target.length();
    }

    @Override
    public char getPriority() {
        return NotConciseTextAnalyzer.priority;
    }
}
