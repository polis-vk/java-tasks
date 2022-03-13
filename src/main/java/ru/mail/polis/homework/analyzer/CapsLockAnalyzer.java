package ru.mail.polis.homework.analyzer;

public class CapsLockAnalyzer implements TextAnalyzer {
    @Override
    public FilterType analyze(String text) {
        String[] currentText = text.split("[ .,;:)(|!]+");
        for (String word : currentText) {
            if (word.length() > 1 && isCapsLock(word)) {
                return FilterType.CAPSLOCK;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return 4;
    }

    public boolean isCapsLock(String word) {
        char[] currentWord = word.toCharArray();
        int capsLockCount = 0;
        for (Character c : currentWord) {
            if (Character.isAlphabetic(c) && c.equals(Character.toUpperCase(c))) {
                capsLockCount++;
            }
        }
        return capsLockCount == word.length();
    }
}
