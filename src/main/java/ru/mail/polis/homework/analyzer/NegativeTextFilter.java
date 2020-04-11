package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter extends SpamFilter {
    private static final String[] EMOTION = {"=(", ":(", ":|"};

    public NegativeTextFilter() {
        super(EMOTION);
    }

    @Override
    public FilterType analyze(String text) {
        if (searchUnacceptableElem(text, EMOTION)) {
            return FilterType.NEGATIVE_TEXT;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
