package ru.mail.polis.homework.analyzer;

public class ExcessiveSpacesAnalyzer implements TextAnalyzer {
    @Override
    public FilterType getFilterType() {
        return FilterType.EXCESSIVE_SPACES;
    }

    @Override
    public boolean analyze(String commentary) {
        return commentary.charAt(0) != ' ' && commentary.charAt(commentary.length() - 1) != ' ' && !commentary.contains("  ");
    }
}
