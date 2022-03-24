package ru.mail.polis.homework.analyzer;

public class NegativeAnalyzer extends ContentSubstringAnalyzer {
    private static final String[] negativeSmiles = new String[]{"=(", ":(", ":|"};

    public NegativeAnalyzer() {
        super(negativeSmiles, FilterType.NEGATIVE_TEXT);
    }
}

