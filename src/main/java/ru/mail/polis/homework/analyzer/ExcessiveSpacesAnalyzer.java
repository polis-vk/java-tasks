package ru.mail.polis.homework.analyzer;

public class ExcessiveSpacesAnalyzer implements TextAnalyzer {
    int priority = 4;


    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean analyze(String commentary) {
        for (int i = 0; i < commentary.length(); i++) {
            char symbol = commentary.charAt(i);
            if (symbol != ' ') {
                continue;
            }
            if (i == 0 || i == commentary.length() - 1 || commentary.charAt(i - 1) == ' ') {
                return false;
            }
        }
        return true;
    }
}
