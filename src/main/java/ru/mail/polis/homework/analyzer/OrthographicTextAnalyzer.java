package ru.mail.polis.homework.analyzer;

public class OrthographicTextAnalyzer implements TextAnalyzer {

    @Override
    public FilterType FilterValue(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if ((text.charAt(i) == 'ш' || text.charAt(i) == 'ж' || text.charAt(i) == 'Ш' || text.charAt(i) == 'Ж') && (text.charAt(i + 1) == 'ы' || (text.charAt(i) == 'Ы'))) {
                return FilterType.ORTHOGRAPHIC_ERR;
            }

        }
        return FilterType.GOOD;
    }
}
