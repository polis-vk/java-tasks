package ru.mail.polis.homework.analyzer.custom;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final FilterType type = FilterType.SPAM;
    private final String[] spamMessages;

    public SpamAnalyzer(String[] spamMessages) {
        this.spamMessages = spamMessages;
    }

    @Override
    public FilterType getType() {
        return type;
    }

    @Override
    public boolean haveProblem(String text) {
        for (String spamMessage : spamMessages) {
            if (text.contains(spamMessage)) {
                return true;
            }
        }
        return false;
    }
}
