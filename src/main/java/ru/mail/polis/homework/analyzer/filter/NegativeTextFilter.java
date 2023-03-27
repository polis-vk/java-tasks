package ru.mail.polis.homework.analyzer.filter;

import ru.mail.polis.homework.analyzer.FilterType;

public class NegativeTextFilter extends AbstractFilter {

    public NegativeTextFilter() {
        super(FilterType.NEGATIVE_TEXT);
    }

    public static String[] getNegativeStrings() {
        return new String[]{"=(", ":(", ":|"};
    }

    @Override
    public boolean applies(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        for (String negativeString : getNegativeStrings()) {
            if (text.contains(negativeString)) {
                return true;
            }
        }
        return false;
    }
}
