package ru.mail.polis.homework.analyzer;

public class MarksFilter extends Filter {
    MarksFilter(FilterType t) {
        super(t);
    }

    @Override
    public FilterType filterText(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) <= '/' && text.charAt(i) >= '!') {
                return type;
            }
        }
        return FilterType.GOOD;
    }
}
