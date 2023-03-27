package ru.mail.polis.homework.analyzer;

public class BreakingBadAnalyzer implements TextAnalyzer {
    private final static String[] AMPHETAMINE_COMPONENTS = {"Flask", "Phenylacetone", "burner", "lab", "Walter White"};

    @Override
    public FilterType getType() {
        return FilterType.COOK;
    }

    @Override
    public FilterType analyze(String text) {
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
