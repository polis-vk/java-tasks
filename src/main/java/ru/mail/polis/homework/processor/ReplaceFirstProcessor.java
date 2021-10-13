package ru.mail.polis.homework.processor;

/**
 * Находит первую подстроку, которая удовлетвроряет регулярному выражению regex, и заменяет ее на подстроку replacement
 * Предполагаем, что параметры корректны
 * <p>
 * Стадия: процессинг
 */
public class ReplaceFirstProcessor implements TextProcessor {
    private final ProcessingStage processingStage = ProcessingStage.PROCESSING;
    private final String regex;
    private final String replacement;

    public ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public int getStage() {
        return processingStage.getStage();
    }

    @Override
    public String process(String text) {
        return text.replaceFirst(regex, replacement);
    }
}
