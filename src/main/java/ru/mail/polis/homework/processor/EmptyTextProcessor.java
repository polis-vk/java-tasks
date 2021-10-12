package ru.mail.polis.homework.processor;

public class EmptyTextProcessor implements TextProcessor {
    @Override
    public ProcessingStage getProcessingStage() {
        return null;
    }

    @Override
    public String process(String text) {
        return text;
    }
}
