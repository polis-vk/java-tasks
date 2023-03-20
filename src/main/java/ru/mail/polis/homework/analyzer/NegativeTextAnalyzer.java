package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    int priority = 3;
    static String[] badSmiles = {"=(", ":(", ":|"};


    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean analyze(String commentary) {
        for (String str : badSmiles) {
            if (commentary.contains(str)) {
                return false;
            }
        }
        return true;
    }
}
