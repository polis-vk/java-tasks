package ru.mail.polis.homework.analyzer;

public class ExcessiveSpacesAnalyzer implements TextAnalyzer {
    private static final int PRIORITY = 4;

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.EXCESSIVE_SPACES;
    }

    @Override
    public boolean analyze(String commentary) {
        return commentary.charAt(0) != ' ' && commentary.charAt(commentary.length() - 1) != ' ' && !commentary.contains("  ");
    }
}
