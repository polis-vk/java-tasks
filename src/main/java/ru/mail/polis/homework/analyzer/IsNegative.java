package ru.mail.polis.homework.analyzer;

public class IsNegative implements TextAnalyzer {

    String[] emotions = new String[]{"=(", ":(", ":|"};
    public FilterType analyze(String arg) {

        for (String s : emotions) {
            if (arg.contains(s)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }

        return FilterType.GOOD;
    }

    private int priority = 2;
    public int getPriority() {
        return priority;
    }
}
