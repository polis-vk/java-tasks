package ru.mail.polis.homework.processor;
/**
 * Схлопывает все пустые символы в один пробел.
 * Более формально, заменить каждую подстроку, удовлетворяющую регулярному выражению \s+ на 1 пробел.
 *
 * Стадия: препроцессинг
 */
public class SquashWhiteSpacesProcessor implements TextProcessor{
    private final ProcessingStage processingStage = ProcessingStage.PREPROCESSING;

    @Override
    public int getStage() {
        return processingStage.getStage();
    }

    @Override
    public String process(String text) {
        return text.replaceAll("\\s+", " ");
    }
}
