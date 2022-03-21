package ru.mail.polis.homework.analyzer;

public class LongSpaceAnalyzer implements TextAnalyzer {

    @Override
    public boolean check(String text) {
        int countSpace = 0;
        for (int i = 0; i< text.length();i++) {
            if (text.charAt(i) == ' ') {
                countSpace++;
            } else {
                countSpace = 0;
            }
            if (countSpace > 1) {
                return true;
            }
         }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }
}
