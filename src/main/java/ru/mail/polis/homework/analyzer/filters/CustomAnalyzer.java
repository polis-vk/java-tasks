package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;


/**
 * ‘ильтр провер€ющий наличие зап€тых перед союзами
 */
public class CustomAnalyzer implements TextAnalyzer {
    private static final String[] GRAMMAR = {", а", ", но", ", что", ", когда", ", потому что"};
    private static final String[] UNIONS = {" а ", " но ", " что ", " когда ", " потому что "};
    private boolean isGrammar;

    public CustomAnalyzer(boolean isGrammar) {
        this.isGrammar = isGrammar;
    }

    @Override
    public FilterType getResult(String text) {
        if (text == null || text.isEmpty()) {
            return FilterType.GOOD;
        }

        if (isGrammar && SpamAnalyzer.analysisText(text, UNIONS)) {
            return SpamAnalyzer.analysisText(text, GRAMMAR) ? FilterType.GOOD : FilterType.CUSTOM;
        }

        return FilterType.GOOD;
    }

    @Override
    public int getIdResult() {
        return 4;
    }
}
