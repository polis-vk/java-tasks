package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;
import ru.mail.polis.homework.objects.StringTasks;

public class SpamAnalyzer implements TextAnalyzer {
    private String[] spamList;

    public SpamAnalyzer(String[] spam) {
        this.spamList = spam;
    }

    public static boolean analysisText(String text, String[] templates) {
        for (String template : templates) {
            if (StringTasks.getSymbolCounts(text, template) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getResult(String text) {
        return analysisText(text, spamList) ? FilterType.SPAM : FilterType.GOOD;
    }

    @Override
    public int getIdResult() {
        return 1;
    }
}
