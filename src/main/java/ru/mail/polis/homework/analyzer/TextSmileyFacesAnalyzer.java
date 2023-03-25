package ru.mail.polis.homework.analyzer;

public class TextSmileyFacesAnalyzer extends TextSpamAnalyzer {
    private static final String[] NEGATIVE_SMILEY_FACES = {"=(", ":(", ":|"};

    public TextSmileyFacesAnalyzer() {
        super(NEGATIVE_SMILEY_FACES);
    }

    @Override
    public FilterType getTypeOfFilter() {
        return FilterType.NEGATIVE_TEXT;
    }
}
