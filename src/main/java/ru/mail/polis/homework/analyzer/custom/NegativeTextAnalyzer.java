package ru.mail.polis.homework.analyzer.custom;

import ru.mail.polis.homework.analyzer.FilterType;

public class NegativeTextAnalyzer extends WrongWordsAnalyzer {
    private final FilterType type = FilterType.NEGATIVE_TEXT;

    public NegativeTextAnalyzer() {
        super(new String[]{"=(", ":(", ":|"});
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
