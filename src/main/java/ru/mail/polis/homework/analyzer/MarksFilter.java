package ru.mail.polis.homework.analyzer;

class MarksFilter implements TextAnalyzer {
    private static final char LEFT_BORDER_MARKS = '!';
    private static final char RIGHT_BORDER_MARKS = '/';

    @Override
    public boolean analyze(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) >= LEFT_BORDER_MARKS && text.charAt(i) <= RIGHT_BORDER_MARKS) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.MARKS;
    }
}
