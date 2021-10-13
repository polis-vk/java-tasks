package ru.mail.polis.homework.processor;

/**
 * Данный обработчик должен оставить первые maxLength символов исходного текста.
 * Если текст короче, то ничего не делать
 * <p>
 * Стадия: постпроцессинг
 */
public class TrimProcessor implements TextProcessor {
    private final ProcessingStage processingStage = ProcessingStage.POSTPROCESSING;
    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int getStage() {
        return processingStage.getStage();
    }

    @Override
    public String process(String text) {
        if (text.length() >= maxLength) {
            return text.substring(0, maxLength);
        }
        return text;
    }
}
