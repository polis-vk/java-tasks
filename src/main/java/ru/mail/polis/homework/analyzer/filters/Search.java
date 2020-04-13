package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class Search {
    public FilterType check(String str, String[] args, FilterType result) {
        for (String arg : args) {
            if (result != FilterType.CUSTOM) {
                if (str.contains(arg)) {
                    return result;
                }
            } else {
                if (str.equals(arg)) {
                    return result;
                }
            }
        }
        return null;
    }
}
