package ru.mail.polis.homework.analyzer;

public class TooMatchRepeatAnalyzer implements TextAnalyzer {

    private final int maxRepeat;

    public TooMatchRepeatAnalyzer(int maxRepeat) {
        this.maxRepeat = maxRepeat;
    }

    @Override
    public FilterType analyze(String text) {
        if (text == null) {
            return FilterType.GOOD;
        }
        int currentRepeat = 1;
        char[] chars = text.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == chars[i - 1]) {
                currentRepeat++;
                if (currentRepeat >= maxRepeat) {
                    return FilterType.CUSTOM;
                }
            }
            else {
                currentRepeat = 1;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
