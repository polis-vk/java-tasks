package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor extends AbstractTextProcessor {
    public SquashWhiteSpacesProcessor() {
        super(ProcessingStage.PREPROCESSING);
    }

    @Override
    public String process(String text) {
        return text.replaceAll("\\s+", " ");
    }
}
