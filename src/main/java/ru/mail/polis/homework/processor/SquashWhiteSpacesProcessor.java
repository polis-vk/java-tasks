package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor{
    private final ProcessingStage stage = ProcessingStage.PREPROCESSING;

    @Override
    public String processText(String text) {
        return text.replaceAll("\\s+", " ");
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}