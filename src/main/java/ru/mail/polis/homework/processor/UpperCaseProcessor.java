package ru.mail.polis.homework.processor;

import java.util.Locale;

/**
 * Обработчик заменяет все символы на заглавные
 * <p>
 * Стадия: постпроцессинг
 */
public class UpperCaseProcessor implements TextProcessor {
    private final ProcessingStage processingStage = ProcessingStage.POSTPROCESSING;

    @Override
    public int getStage() {
        return processingStage.getStage();
    }

    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}
