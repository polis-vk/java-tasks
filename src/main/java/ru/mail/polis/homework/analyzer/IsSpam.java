package ru.mail.polis.homework.analyzer;

public class IsSpam implements TextAnalyzer {

    String[] textSpam;
    public IsSpam(String[] spam) {
        textSpam = spam;
    }

    public FilterType analyze(String arg) {

        for (String s : textSpam) {
            if (arg.contains(s)) {
                return FilterType.SPAM;
            }
        }

        return FilterType.GOOD;
    }

    private int priority = 0;
    public int getPriority() {
        return priority;
    }
}
