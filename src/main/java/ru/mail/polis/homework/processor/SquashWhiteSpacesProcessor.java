package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    private static final ProcessingStage stage = ProcessingStage.PRE_PROCESSING;

    @Override
    public String process(String text) {
        return text.replaceAll("\\s+", " ");
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
