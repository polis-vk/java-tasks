package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends Analyzer implements TextAnalyzer {
    NegativeTextAnalyzer() {
        super(new String[]{"=(", ":(", ":|"});
    }

    public void setType() {
        this.type = FilterType.NEGATIVE_TEXT;
    }
}

