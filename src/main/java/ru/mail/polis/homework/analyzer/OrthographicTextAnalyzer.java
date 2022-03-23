package ru.mail.polis.homework.analyzer;

public class OrthographicTextAnalyzer implements TextAnalyzer {

    @Override
    public FilterType FilterValue(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            char currentChar = Character.toUpperCase(text.charAt(i));
            if ((currentChar == 'Ш' || currentChar == 'Ж') && Character.toUpperCase(text.charAt(i + 1)) == 'Ы') {
                return FilterType.ORTHOGRAPHIC_ERROR;
            }
        }
        return FilterType.GOOD;
    }
}
