package ru.mail.polis.homework.analyzer;

class MarksFilter implements TextAnalyzer {
    private static final char leftBorderMarks = '!', rightBorderMarks = '/';

    @Override
    public boolean filterText(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) >= leftBorderMarks && text.charAt(i) <= rightBorderMarks) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return FilterType.MARKS;
    }
}
