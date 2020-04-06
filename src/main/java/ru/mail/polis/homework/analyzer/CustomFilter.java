package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {


    public static long priority;


    //проверка, того что ни один сивол не составляет более 60% текста
    @Override
    public FilterType analyze(String str) {
        int counSameSymbol;
        for (char c : str.toCharArray()) {
            counSameSymbol = 0;
            for (int i = 0; i < str.length(); i++) {
                if (c == str.charAt(i)) {
                    counSameSymbol++;
                }
            }
            if ((double) counSameSymbol / str.length() >= 0.6) {
                return FilterType.CUSTOM;
            }
        }
        return null;
    }

    @Override
    public long getPriority() {
        return priority;
    }


}
