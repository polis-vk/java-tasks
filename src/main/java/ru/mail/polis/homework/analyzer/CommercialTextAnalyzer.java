package ru.mail.polis.homework.analyzer;

public class CommercialTextAnalyzer implements TextAnalyzer {
    private final String[] commercialStrings;
    private final FilterType type = FilterType.COMMERCIAL_TEXT;

    CommercialTextAnalyzer(String[] commercialStrings) {
        this.commercialStrings = commercialStrings;
    }
    @Override
    public boolean resultResearch(String str) {
        for (String elem : commercialStrings) {
            if (str.contains(elem)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public FilterType getType() {
        return type;
    }
}
