package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;
import ru.mail.polis.homework.objects.StringTasks;

public class SpamAnalyzer implements TextAnalyzer {
    private String[] spamList;

    public SpamAnalyzer(String[] spam) {
        this.spamList = spam;
    }

    @Override
    public FilterType getResult(String text) {
        for (String spamWord : spamList) {
            if (StringTasks.getSymbolCounts(text, spamWord) > 0) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int getIdResult() {
        return 1;
    }
}
