package ru.mail.polis.homework.analyzer;
//Проверяет эмоционально ли произносят предложение
public class EmotionalTextFilter extends Analyzing implements TextAnalyzer {

    private static final String[] SIGN = {"!", "?", "..."};

    @Override
    public FilterType analyze(String text) {
        return analyzing(text, SIGN, FilterType.EMOTIONAL);
    }
}
