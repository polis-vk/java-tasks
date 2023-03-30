package ru.mail.polis.homework.analyzer;

public class NegativeAnalyzer implements TextAnalyzer {

    @Override
    public boolean isBadText(String text) {
        char[] txtArr = text.toCharArray();
        char[] emotions = {')', '(', '|'};
        for (int i = 0; i < txtArr.length - 1; i++) {
            if (txtArr[i] == ':') {
                for (int j = 0; j < emotions.length; j++) {
                    if (txtArr[i + 1] == emotions[j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public FilterType getTypeFilter() {
        return FilterType.NEGATIVE_TEXT;
    }
}
