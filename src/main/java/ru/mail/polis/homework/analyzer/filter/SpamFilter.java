package ru.mail.polis.homework.analyzer.filter;

import ru.mail.polis.homework.analyzer.FilterType;

public class SpamFilter extends AbstractFilter {

    private final String[] spam;

    public SpamFilter(String[] spam) {
        super(FilterType.SPAM);
        this.spam = spam;
    }

    public String[] getSpam() {
        return spam;
    }

    @Override
    public boolean applies(String text) {
        if ((text == null) || text.isEmpty()) {
            return false;
        }
        for (String spamString : spam) {
            if (text.contains(spamString)) {
                return true;
            }
        }
        return false;
    }
}
