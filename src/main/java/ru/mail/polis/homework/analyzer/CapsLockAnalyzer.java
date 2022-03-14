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
