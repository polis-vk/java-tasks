package ru.mail.polis.homework.analyzer;

public class TextSmileyFacesAnalyzer implements TextAnalyzer {
    private final String[] NEGATIVE_SMILEY_FACES = {"=(", ":(", ":|"};

    @Override
    public boolean analyzeText(String text) {
        for (String notSmileFace : NEGATIVE_SMILEY_FACES) {
            if (text.contains(notSmileFace)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getTypeOfFilter() {
        return FilterType.NEGATIVE_TEXT;
    }
}
