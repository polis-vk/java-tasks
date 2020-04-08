package ru.mail.polis.homework.analyzer;
//Проверяет эмоционально ли произносят предложение
public class EmotionalTextFilter implements TextAnalyzer {

    private static final FilterType type = FilterType.EMOTIONAL;
    private static final String[] sign = {"!", "?", "..."};

    @Override
    public FilterType analyze(String text) {
        for (String emoteSign : sign) {
            if (text.contains(emoteSign)) {
                return type;
            }
        }
        return good;
    }
}
