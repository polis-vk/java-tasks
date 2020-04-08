package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter extends SpamFilter {
    private static final String[] unacceptableElem = {"=(", ":(", ":|"};

    public NegativeTextFilter() {
        super(unacceptableElem);
    }

    @Override
    public FilterType analyze(String text) {
        if (searchUnacceptableElem(text, unacceptableElem)) {
            return FilterType.NEGATIVE_TEXT;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType priority() {
        return FilterType.NEGATIVE_TEXT;
    }
}
