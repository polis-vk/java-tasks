package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.objects.StringTasks;

public class SimpleTextFilter {
    private final String[] templates;

    public SimpleTextFilter(String[] templates) {
        this.templates = templates;
    }

    public boolean analysisText(String text) {
        for (String template : templates) {
            if (StringTasks.getSymbolCounts(text, template) > 0) {
                return true;
            }
        }
        return false;
    }
}
