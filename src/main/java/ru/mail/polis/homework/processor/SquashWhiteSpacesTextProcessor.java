package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesTextProcessor implements TextProcessor {

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PREPROCESSING;
    }

    @Override
    public String processText(String text) {
        return text == null ? null : text.replaceAll("\\s+", " ");
    }

}
