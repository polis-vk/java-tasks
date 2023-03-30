package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private final String[] negativeEmojis = {":|", ":(", "=("};

    @Override
    public boolean analyze(String text) {
        for (String emoji : negativeEmojis) {
            if (text.contains(emoji)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
