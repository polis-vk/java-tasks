package ru.mail.polis.homework.analyzer;

import static ru.mail.polis.homework.analyzer.FilterType.GOOD;
import static ru.mail.polis.homework.analyzer.FilterType.NEGATIVE_TEXT;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private final String[] negativeText = new String[]{ "=(", ":(", ":|"};
    private final byte priority = 2;

    @Override
    public byte getPriority() {
        return priority;
    }

    @Override
    public FilterType analyze(String text) {
        FilterType result = new SpamTextAnalyzer(negativeText).analyze(text);
        return result == GOOD ? GOOD : NEGATIVE_TEXT;
    }
}
