package ru.mail.polis.homework.analyzer;

public class SpamFilter extends Filter {
    String[] spam;

    SpamFilter(FilterType t, String[] spam) {
        super(t);
        this.spam = new String[spam.length];
        System.arraycopy(spam, 0, this.spam, 0, spam.length);
    }

    @Override
    public FilterType filterText(String text) {
        for (String s :
                spam) {
            if (text.contains(s)) {
                return type;
            }
        }
        return FilterType.GOOD;
    }
}
