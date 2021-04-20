package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.TextAnalyzer;

public abstract class Filter implements TextAnalyzer {

    protected int order = Integer.MAX_VALUE;
    // очередной в очереди - чем меньше, тем приоритетнее

    public int getOrder() {
        return order;
    }
}
