package ru.mail.polis.homework.analyzer;

public class InclusionFinder {
    public boolean checkInclusion(String string, String[] subString) {
        if (string == null) {
            return true;
        }
        for (String subStr : subString) {
            if (string.contains(subStr)) {
                return false;
            }
        }
        return true;
    }
}
