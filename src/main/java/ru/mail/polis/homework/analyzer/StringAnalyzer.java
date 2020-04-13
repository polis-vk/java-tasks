package ru.mail.polis.homework.analyzer;

// вынес копипасту из классов IsSpam и IsNegative, дабы все выглядело православно и пасты больше не было :D
public class StringAnalyzer {
    FilterType stringAnalyzer(String arg, String[] arrayToCheck, FilterType type) {
        for (String s : arrayToCheck) {
            if (arg.contains(s)) {
                return type;
            }
        }
        return FilterType.GOOD;
    }
}
