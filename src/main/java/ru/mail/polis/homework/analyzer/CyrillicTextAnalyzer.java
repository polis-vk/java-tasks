package ru.mail.polis.homework.analyzer;

public class CyrillicTextAnalyzer implements TextAnalyzer {

    @Override
    public boolean analyze(String text) {
        if (text.isEmpty()) {
            return false;
        }

        for (char c : text.toCharArray()) {
            if (!isValidSymbol(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CYRILLIC_TEXT;
    }

    private boolean isValidSymbol(char c) {
        return isCyrillicLetter(c) || isPunctuationMark(c);
    }

    private boolean isPunctuationMark(char c) {
        return c == '.' || c == ',' || c == ' ';
    }

    private boolean isCyrillicLetter(char c) {
        return Character.UnicodeBlock.of(c).equals(Character.UnicodeBlock.CYRILLIC);
    }
}
