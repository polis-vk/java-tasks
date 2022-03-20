package ru.mail.polis.homework.analyzer;

public class CapsLockAnalyzer implements TextAnalyzer {
    private final static String REGEX = "[ .,;:)(|!]+";
    private final static FilterType FILTER_TYPE = FilterType.CAPSLOCK;

    @Override
    public boolean analyze(String text) {
        String[] currentText = text.split(REGEX);
        for (String word : currentText) {
            if (word.length() > 1 && isCapsLock(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getAnalyzerType() {
        return FILTER_TYPE;
    }

    public boolean isCapsLock(String word) {
        char[] currentWord = word.toCharArray();
        for (Character c : currentWord) {
            if (!Character.isAlphabetic(c) || !c.equals(Character.toUpperCase(c))) {
                return false;
            }
        }
        return true;
    }
}
