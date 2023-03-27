package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends Analyzer implements TextAnalyzer {

    NegativeTextAnalyzer() {
        this.arrayStrings = new String[]{"=(", ":(", ":|"};
        this.type = FilterType.NEGATIVE_TEXT;
    }
}

