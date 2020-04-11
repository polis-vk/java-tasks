package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    private final String[] unacceptableElem;

    public SpamFilter(String[] spam) {
        this.unacceptableElem = spam;
    }

    protected static boolean searchUnacceptableElem(String text, String[] unacceptableElem) {
        for (String elem : unacceptableElem) {
            if (text.contains(elem)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType analyze(String text) {
        if (searchUnacceptableElem(text, unacceptableElem)) {
            return FilterType.SPAM;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
