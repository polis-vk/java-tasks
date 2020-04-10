package ru.mail.polis.homework.analyzer;

public class NegativeFilter extends SpamFilter {

    private static final String[] NEGATIVE = {"=(", ":(", ":|"};

    public NegativeFilter() {

        super(NEGATIVE);

    }

    @Override
    public FilterType analyze(String str) {
        if (super.analyze(str) == FilterType.SPAM) {
            return FilterType.NEGATIVE_TEXT;
        }
        return null;

    }

    @Override
    public long getPriority() {
        return FilterType.NEGATIVE_TEXT.getPriority();
    }


}
