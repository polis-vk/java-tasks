package ru.mail.polis.homework.analyzer;

public class BreakingBadAnalyzer implements TextAnalyzer {
    private final int SIGNIFICANCE = 4;
    private final String[] AMPHETAMINE_COMPONENTS = {"Flask", "Phenylacetone", "burner", "lab", "Walter White"};
    private final String BETTER_CALL_SAUL = "Saul Goodman";

    @Override
    public int getSignificance() {
        return SIGNIFICANCE;
    }

    @Override
    public FilterType analyze(String text) {
        if (text.contains(BETTER_CALL_SAUL)) {
            return FilterType.BETTER_CALL_SAUL;
        }
        for (String component : AMPHETAMINE_COMPONENTS) {
            if (!text.contains(component)) {
                return FilterType.GOOD;
            }
        }
        return FilterType.COOK;
    }
}
