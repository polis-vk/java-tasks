package ru.mail.polis.homework.analyzer;

public class IsSpam implements TextAnalyzer {

    private int priority = 0;
    private String[] textSpam;
    public IsSpam(String[] spam) {
        textSpam = spam;
    }

    @Override
    public FilterType analyze(String arg) {
        for (String s : textSpam) {
            if (arg.contains(s)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int newPriority) {
        this.priority = newPriority;
    }
}
