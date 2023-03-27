package ru.mail.polis.homework.analyzer;

import static ru.mail.polis.homework.analyzer.Search.search;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] BAD_SMILES = {"=(", ":(", ":|"};

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean analyze(String commentary) {
        return search(commentary, BAD_SMILES);
    }
}
