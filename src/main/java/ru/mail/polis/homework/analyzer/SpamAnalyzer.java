package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends Analyzer implements TextAnalyzer {

    SpamAnalyzer(String[] spamStrings) {
        super(spamStrings);
    }

    public void setType() {
        this.type = FilterType.SPAM;
    }
}

