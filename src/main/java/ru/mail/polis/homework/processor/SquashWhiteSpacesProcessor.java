package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PREPROCESSING;
    }

    @Override
    public void execute(String str) {
        str = str.replaceAll("\\s+", " ");
    }
}
