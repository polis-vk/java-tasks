package ru.mail.polis.homework.analyzer;

abstract class Filter implements TextAnalyzer {
    FilterType type;

    Filter(FilterType t) {
        type = t;
    }

    @Override
    public abstract FilterType filterText(String text);

    @Override
    public int getTypeNumber() {
        return type.getNumber();
    }
}
