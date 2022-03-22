package ru.mail.polis.homework.analyzer;

public class HasSpaceAfterPointAnalyzer implements TextAnalyzer { //Находит такие строки, в которых после знака препинания нет пробела

    private final String[] PUNCTUATION = {".", ",", "!", "?", ":", ";"};

    @Override
    public boolean analyze(String text) {
        for (String mark : PUNCTUATION) {
            if (text.contains(mark) && text.length() - 1 > text.indexOf(mark)) {
                char nextChar = text.charAt(text.indexOf(mark) + 1);
                if (nextChar != ' ' && nextChar != '(' && nextChar != ')' && nextChar != '|') {
                    return true;
                }
            }
        }
        return false;
    }

    public FilterType getType() {
        return FilterType.HAS_SPACE_AFTER_POINT;
    }
}
