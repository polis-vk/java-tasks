package ru.mail.polis.homework.analyzer;

public class ExcessiveSpacesAnalyzer implements TextAnalyzer {
    private static final int priority = 4;
    private static final FilterType filterType = FilterType.EXCESSIVE_SPACES;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }

    @Override
    public boolean analyze(String commentary) {
        return commentary.charAt(0) != ' ' && commentary.charAt(commentary.length() - 1) != ' ' && !commentary.contains("  ");
    }
}
