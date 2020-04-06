package ru.mail.polis.homework.analyzer;

public class FindInText {
    public static FilterType find(String[] templateArray, String str, FilterType neededType) {
        for (String item : templateArray) {
            if (str.contains(item)) {
                return neededType;
            }
        }
        return FilterType.GOOD;
    }
}
