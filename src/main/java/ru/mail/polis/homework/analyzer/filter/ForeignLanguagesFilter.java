package ru.mail.polis.homework.analyzer.filter;

import ru.mail.polis.homework.analyzer.FilterType;

public class ForeignLanguagesFilter extends AbstractFilter {

    public ForeignLanguagesFilter() {
        super(FilterType.FOREIGN_LANGUAGES);
    }

    @Override
    public boolean applies(String text) {
        for (int i = 0; i < text.length(); i++) {
            char currSymbol = text.charAt(i);
            if (Character.isLetter(currSymbol) && notCyrillicLetter(currSymbol)) {
                return true;
            }
        }
        return false;
    }

    private boolean notCyrillicLetter(char letter) {
        return (Character.UnicodeBlock.of(letter) != Character.UnicodeBlock.CYRILLIC);
    }
}
