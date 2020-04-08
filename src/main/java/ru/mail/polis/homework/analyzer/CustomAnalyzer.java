package ru.mail.polis.homework.analyzer;

public class CustomAnalyzer implements TextAnalyzer {
    private String[] symbols;
    private int[] maxSymbolCount;
    private Integer priority;

    public CustomAnalyzer(String[] symbols, int[] maxSymbolCount) {
        this.symbols = symbols;
        this.maxSymbolCount = maxSymbolCount;
        priority = FilterType.CUSTOM.ordinal();
    }

    public FilterType analysis(String text) {
        int i = 0;
        for (String symbol : symbols) {
            if (getSymbolCounts(text, symbol) > maxSymbolCount[i]) {
                return FilterType.CUSTOM;
            }
            i++;
        }
        return FilterType.GOOD;
    }

    public static int getSymbolCounts(String str, String symbol) {
        return str.length() - str.replace(symbol, "").length();
    }

    public Integer getPriority() {
        return priority;
    }
}
