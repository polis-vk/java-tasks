package ru.mail.polis.homework.analyzer;

public class CorrectStructureAnalyzer implements TextAnalyzer {
    @Override
    public boolean isAnalyze(String str) {
        char[] arrayOfStr = str.toCharArray();
        if (arrayOfStr[0] != Character.toUpperCase(arrayOfStr[0])) {
            return false;
        }

        return arrayOfStr[str.length() - 1] == '.'
                | arrayOfStr[str.length() - 1] == '!'
                | arrayOfStr[str.length() - 1] == '?';
    }

    @Override
    public FilterType getType() {
        return FilterType.CORRECT_STRUCTURE;
    }
}
