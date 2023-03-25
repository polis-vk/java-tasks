package ru.mail.polis.homework.analyzer;

public class BreakingBadAnalyzer implements TextAnalyzer {
    private final static int SIGNIFICANCE = 4;
    private final static String[] AMPHETAMINE_COMPONENTS = {"Flask", "Phenylacetone", "burner", "lab", "Walter White"};
    private final static String SAUL_FULL_NAME = "Saul Goodman";

    @Override
    public int getSignificance() {
        return SIGNIFICANCE;
    }

    @Override
    public FilterType analyze(String text) {
        if (text.contains(SAUL_FULL_NAME)) {
            return FilterType.BETTER_CALL_SAUL;
        }
        int componentsCounter = 0;
        for (String component : AMPHETAMINE_COMPONENTS) {
            if (text.contains(component)) {
                componentsCounter++;
            }
        }
        if (componentsCounter >= AMPHETAMINE_COMPONENTS.length / 2) {
            return FilterType.COOK;
        }
        return FilterType.GOOD;
    }
}
