package ru.mail.polis.homework.processor;

// Stage:PREPROCESSING
public class SquashWhiteSpacesTextProcessor implements TextProcessor {

    @Override
    public int ordinal() {
        return ProcessingStage.PREPROCESSING.ordinal;
    }

    @Override
    public String processText(String text) {
        return text.replaceAll("\\s+", " ");
    }

}
