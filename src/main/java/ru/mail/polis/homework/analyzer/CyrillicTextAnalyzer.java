package ru.mail.polis.homework.analyzer;

public class CyrillicTextAnalyzer implements TextAnalyzer {

    @Override
    public boolean analyze(String text) {
        if (text.isEmpty()) {
            return false;
        }

        for (int i = 0; i != text.length(); ++i) {
            if (!isValidSymbol(text.charAt(i))) {
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
        return Character.UnicodeBlock.of(c).equals(Character.UnicodeBlock.GENERAL_PUNCTUATION);
    }

    private boolean isCyrillicLetter(char c) {
        return Character.UnicodeBlock.of(c).equals(Character.UnicodeBlock.CYRILLIC);
    }
}
