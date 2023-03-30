package ru.mail.polis.homework.analyzer.filter;

import ru.mail.polis.homework.analyzer.FilterType;

public class SpamFilter extends AbstractFilter {

    private final String[] spam;

    public SpamFilter(String[] spam) {
        super(FilterType.SPAM);
        this.spam = spam;
    }

    @Override
    public boolean applies(String text) {
        if (text.isEmpty()) {
            return false;
        }
        return super.textContains(text, spam);
    }
}
