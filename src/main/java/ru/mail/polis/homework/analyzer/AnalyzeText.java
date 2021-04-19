package ru.mail.polis.homework.analyzer;

abstract public class AnalyzeText {
    public static boolean analyzeText(String text, String[] array) {
        for (String strangeWords : array) {
            if (text.contains(strangeWords)) {
                return true;
            }
        }
        return false;
    }
}
