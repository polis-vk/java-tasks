package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

/**
 * Фильтр проверяющий наличие запятых перед союзами
 */
public class CustomFilter implements TextAnalyzer {
    private static final String[] GRAMMAR = {", а", ", но", ", что", ", когда", ", потому что"};
    private static final String[] UNIONS = {" а ", " но ", " что ", " когда ", " потому что "};
    private final SimpleTextFilter grammarFilter;
    private final SimpleTextFilter unionsFilter;
    private final boolean isGrammar;

    public CustomFilter(boolean isGrammar) {
        this.isGrammar = isGrammar;
        grammarFilter = new SimpleTextFilter(GRAMMAR);
        unionsFilter = new SimpleTextFilter(UNIONS);
    }

    @Override
    public boolean analysis(String text) {
        if (isGrammar && unionsFilter.analysisText(text)) {
            return !grammarFilter.analysisText(text);
        }

        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }
}
