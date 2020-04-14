package ru.mail.polis.homework.analyzer;

import static ru.mail.polis.homework.analyzer.TextAnalyzer.good;

public class Analyzing {

    public FilterType analyzing(String text, String[] args, FilterType type) {
        for (String arg : args) {
            if (text.contains(arg)) {
                return type;
            }
        }
        return good;
    }
}
