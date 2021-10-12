package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    private final ProcessingStage stage;

    public SquashWhiteSpacesProcessor(ProcessingStage stage) {
        this.stage = stage;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return stage;
    }

    @Override
    public String process(String text) {
        return text.replaceAll("\\s+", " ");
    }
}
